package org.example.TaskSeven;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PartTwo {

    private static Folder bestDirectory;
    private static final int TOTAL_DISK_SPACE = 70000000;
    private static final int SPACE_NEEDED_FOR_UPDATE = 30000000;

    // could have used this method instead of having `sumOfFoldersWithSpecificSize`
    private static int calculateFolderSize(Folder folder) {
        int totalFileSize = 0;
        int totalFolderSize = 0;
        if (folder.getFiles().size() > 0) {
            totalFileSize = folder.getFiles().stream()
                    .map(MyFile::getSize)
                    .mapToInt(Integer::intValue)
                    .sum();
        }

        if (folder.getFolders().size() > 0) {
            for(int i = 0; i < folder.getFolders().size(); i++) {
                totalFolderSize += calculateFolderSize(folder.getFolders().get(i));
            }
        }

        folder.setSize(totalFileSize + totalFolderSize);
        return totalFolderSize + totalFileSize;
    }

    public static boolean isNumber(String str) {
        for(int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        try {
            File myObj = new File("src/main/resources/task_seven/input");
            Scanner myReader = new Scanner(myObj);

            Folder activeFolder = null;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                String input = null;
                if (data.charAt(0) == '$') {
                    input = Character.toString(data.charAt(2)) + Character.toString(data.charAt(3));
                }

                // if input null then reading the file/folder line
                if (input == null) {

                    assert activeFolder != null;
                    if (data.startsWith("dir")) {
                        String folderName = data.split(" ")[1];
                        activeFolder.addFolder(new Folder(folderName, activeFolder));
                    } else if (isNumber(data.split(" ")[0])) {
                        String fileName = data.split(" ")[1];
                        activeFolder.addFile(new MyFile(fileName, Integer.parseInt(data.split(" ")[0])));
                    }

                } else if (input.equals("cd")) {
                    String[] split = data.split(" cd ");
                    String targetFolder = split[1];

                    if (!targetFolder.equals("..")) {
                        if (activeFolder == null) {
                            activeFolder = new Folder(targetFolder, null);
                        } else {
                            // entering the child folder of activeFolder
                            for(int i = 0; i < activeFolder.getFolders().size(); i++) {
                                Folder folder = activeFolder.getFolders().get(i);
                                if (folder.getName().equals(targetFolder)) {
                                    activeFolder = folder;
                                }
                            }
                        }
                    } else {
                        assert activeFolder != null;
                        activeFolder = activeFolder.getParent();
                    }

                }

            }

            // calculate size for each folder:
            // go to root:
            assert activeFolder != null;
            while (!activeFolder.getName().equals("/")) {
                activeFolder = activeFolder.getParent();
            }

            // PART TWO:

            // total folder size
            int totalFolderSize = calculateFolderSize(activeFolder);
            System.out.println(totalFolderSize);

            int currentUnusedSpace = TOTAL_DISK_SPACE - totalFolderSize;
            System.out.println(currentUnusedSpace);

            findBestDir(activeFolder, totalFolderSize);

            System.out.println(bestDirectory.getSize());

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void findBestDir(Folder folder, int totalFolderSize) {
        for (int i = 0; i < folder.getFolders().size(); i++) {
            Folder currFolder = folder.getFolders().get(i);
            // the space in fs all folders occupy without the current folder
            int potentialTotalFolderSize = totalFolderSize - currFolder.getSize();
            int unusedSpace = TOTAL_DISK_SPACE - potentialTotalFolderSize;
            if (unusedSpace >= SPACE_NEEDED_FOR_UPDATE && bestDirectory == null) {
                bestDirectory = currFolder;
            } else if (unusedSpace >= SPACE_NEEDED_FOR_UPDATE && currFolder.getSize() < bestDirectory.getSize()) {
                bestDirectory = currFolder;
            }
            findBestDir(currFolder, totalFolderSize);
        }
    }
}