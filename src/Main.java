public class Main {
    public static void main(String[] args) {
        ArchivingDirectory archDir = new ArchivingDirectory();
        SendEmail sendEmail = new SendEmail();

        try {
            archDir.Zip();
            sendEmail.sendEmailWithAttachment(Constants.sourcePath + Constants.zipFileName,
                    Constants.recipient);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}