package uz.asbt.digid.nodeservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import uz.asbt.digid.nodeservice.model.Arm;

import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

@RefreshScope
@Service
public class ArmServiceImpl implements ArmService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${node.arms.dir}")
    private String armsDir;

    @Value("${node.logs.dir}")
    private String logsDir;

    @Override
    public List<Arm> list() throws Exception {
        List<Arm> arms = new ArrayList<>();
        Path dirPath = Paths.get(armsDir);
        DirectoryStream<Path> pathes = Files.newDirectoryStream(dirPath, "*.jar");
        for (Path path : pathes) {
            JarFile jarFile = new JarFile(path.toFile());
            Manifest manifest = jarFile.getManifest();
            Arm arm = new Arm();
            manifest.getMainAttributes().entrySet().stream().forEach(i -> logger.info(String.format("Key: %s, Value: %s", i.getKey(), i.getValue())));
            arm.setId(Integer.parseInt(manifest.getMainAttributes().getValue("Arm-ID")));
            arm.setTitle(manifest.getMainAttributes().getValue("Title"));
            arm.setDescription(manifest.getMainAttributes().getValue("Description"));
            arm.setVersion(manifest.getMainAttributes().getValue("Version"));
            arm.setJar(new File(jarFile.getName()).getName());
            arms.add(arm);
        }
        return arms;
    }

    @Override
    public Arm start(Arm arm) throws Exception {
        if (arm.getId() == 0 || arm.getJar() == null || arm.getJar().isEmpty()) {
            throw new Exception("Invalid arm info");
        }
        logger.info("Begin getting arm list");
        List<Arm> arms = list();
        Arm localArm = null;
        for (Arm armi : arms) {
            logger.info("Arm jar: {}", armi.getJar());
            if (armi.getId() == arm.getId() && arm.getJar().equals(armi.getJar())) {
                localArm = armi;
                break;
            }
        }
        if (localArm == null) {
            throw new Exception("Failed to find the arm : " + arm.getId());
        }
        String javaHome = getJavaHome();
        logger.info("JAVA_HOME: {}", javaHome);
        String armHome = armsDir;
        logger.info("ARM_HOME: {}", armHome);
        File f = new File(armHome);
        f = new File(f, localArm.getJar());
        logger.info("Absolute path is: {}", f.getAbsolutePath());
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec(javaHome + " -jar " + f.getAbsolutePath());
        final File logFile = new File(logsDir);
        final File lgf = new File(logFile, localArm.getJar().substring(0, localArm.getJar().length() - 3) + "-out.log");
        new Thread(() -> {
            try {
                Files.copy(proc.getInputStream(), Paths.get(lgf.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        }).start();
        return localArm;
    }

    @Override
    public List<Arm> startAll() throws Exception {
        List<Arm> errorList = new ArrayList<>();
        List<Arm> arms = list();
        for (Arm armi : arms) {
            try {
                String javaHome = getJavaHome();
                String armHome = armsDir;
                File f = new File(armHome);
                f = new File(f, armi.getJar());
                Runtime runtime = Runtime.getRuntime();
                Process proc = runtime.exec(javaHome + " -jar " + f.getAbsolutePath());
                final File logFile = new File(logsDir);
                final File lgf = new File(logFile, armi.getJar().substring(0, armi.getJar().length() - 3) + ".log");
                new Thread(() -> {
                    try {
                        Files.copy(proc.getInputStream(), Paths.get(lgf.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception ex) {
                        logger.error(ex.getMessage());
                    }
                }).start();
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                errorList.add(armi);
            }
        }
        return errorList;
    }

    private String getJavaHome() { // Returns java commands file
        String javaHome = System.getProperty("java.home");
        File f = new File(javaHome);
        f = new File(f, "bin");
        f = new File(f, "java");
        return f.getAbsolutePath();
    }
}
