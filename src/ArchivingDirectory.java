import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ArchivingDirectory {
    public void Zip() throws Exception {
        FileOutputStream fout = new FileOutputStream(Constants.zipFileName);
        ZipOutputStream zout = new ZipOutputStream(fout, StandardCharsets.UTF_8);

        File fileSource = new File(Constants.sourcePath);

        addDirectory(zout, fileSource);

        zout.close();
        System.out.println("Ваш проект архивирован!");
    }

    public void addDirectory(ZipOutputStream zout, File fileSource) throws Exception {
        File[] files = fileSource.listFiles();
        String testName = "testZip.zip";

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                addDirectory(zout, files[i]);
                continue;
            }

            if (!files[i].getName().equals(testName)) {
                FileInputStream fis = new FileInputStream(files[i]);
                zout.putNextEntry(new ZipEntry(files[i].getPath()));

                byte[] buffer = new byte[4048];
                int length;
                while ((length = fis.read(buffer)) > 0)
                    zout.write(buffer, 0, length);
                zout.closeEntry();
                fis.close();
            }
        }
    }
}
