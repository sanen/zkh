package com.language.java.zip;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.*;

/**
 * @author zhangkeh
 */
public class ZipUtil {

    private final static Logger logger = Logger.getLogger(ZipUtil.class.getName());
    private static final int BUFFER = 1024 * 10;

    /**
     * 将指定目录压缩到和该目录同名的zip文件，自定义压缩路径
     * 
     * @param sourceFilePath
     *            目标文件路径
     * @param zipFilePath
     *            指定zip文件路径
     * @return
     */
    public static boolean zip(String sourceFilePath, String zipFilePath, String zipFileName) {
        boolean result = false;
        File source = new File(sourceFilePath);
        if (!source.exists()) {
            logger.info(sourceFilePath + " doesn't exist.");
            return result;
        }
        if (!source.isDirectory()) {
            logger.info(sourceFilePath + " is not a directory.");
            return result;
        }
        File zipFile = new File(zipFilePath + File.separator + zipFileName + ".zip");
        if (zipFile.exists()) {
            logger.info(zipFile.getName() + " is already exist.");
            return result;
        } else {
            if (!zipFile.getParentFile().exists()) {
                if (!zipFile.getParentFile().mkdirs()) {
                    logger.info("cann't create file " + zipFileName);
                    return result;
                }
            }
        }
        logger.info("creating zip file...");
        FileOutputStream dest = null;
        ZipOutputStream out = null;
        try {
            dest = new FileOutputStream(zipFile);
            CheckedOutputStream checksum = new CheckedOutputStream(dest, new Adler32());
            out = new ZipOutputStream(new BufferedOutputStream(checksum));
            out.setMethod(ZipOutputStream.DEFLATED);
            compress(source, out, source.getName());
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (result) {
            logger.info("done.");
        } else {
            logger.info("fail.");
        }
        return result;
    }

    private static void compress(File file, ZipOutputStream out, String mainFileName) {
        int index = file.getAbsolutePath().indexOf(mainFileName);
        String entryName = file.getAbsolutePath().substring(index);
        // System.out.println(entryName);
        if (file.isFile()) {
            FileInputStream fi = null;
            BufferedInputStream origin = null;
            try {
                fi = new FileInputStream(file);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(entryName);
                out.putNextEntry(entry);
                byte[] data = new byte[BUFFER];
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (origin != null) {
                    try {
                        origin.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (file.isDirectory()) {
            try {
                out.putNextEntry(new ZipEntry(entryName + File.separator));
            } catch (IOException e) {
                e.printStackTrace();
            }
            File[] fs = file.listFiles();
            if (fs != null && fs.length > 0) {
                for (File f : fs) {
                    compress(f, out, mainFileName);
                }
            }
        }
    }

    private static String root;

    public static void zipFile(File file) {

        if (file.exists()) {
            try {
                root = file.getName();
                ZipOutputStream out = null;
                try {
                    out = new ZipOutputStream(new FileOutputStream(new File("C:\\temp\\" + file.getName() + ".zip")));
                    zipFile(file, out);
                    System.out.println("zip this folder " + file.getName() + " is successful");
                } catch (Exception e) {
                    System.out.println("throw the exception, exception message is " + e.getMessage());
                } finally {
                    if (out != null) {
                        try {
                            out.finish();
                        } catch (IOException e) {
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("file not exists !!! ");
        }

    }

    public static void zipFile(File file, ZipOutputStream out) throws Exception {
        String path = file.getPath();
        int index = path.lastIndexOf(root) + root.length();
        if (file.isDirectory()) {
            File[] subFile = file.listFiles();
            for (File f : subFile) {
                zipFile(f, out);
            }
        } else {
            zipFile(file, path.substring(index + 1), out);
        }
    }

    public static void zipFile(File file, String zipPath, ZipOutputStream out) throws Exception {
        if (file.isDirectory()) {
            out.putNextEntry(new ZipEntry(zipPath + "/" + file.getName() + "/"));
        } else {
            out.putNextEntry(new ZipEntry(zipPath));
            FileInputStream in = new FileInputStream(file);
            byte[] b = new byte[1000];
            int len = -1;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            in.close();
        }
    }

    /**
     * 将zip文件解压到指定的目录，该zip文件必须是使用该类的zip方法压缩的文件
     * 
     * @param zipFile
     *            要解压的zip文件
     * @param destPath
     *            指定解压到的目录
     * @return
     */
    public static boolean unzip(File zipFile, String destPath) {
        boolean result = false;

        // chceck zip file whether exist
        if (!zipFile.exists()) {
            logger.info(zipFile.getName() + " doesn't exist.");
            return result;
        }

        // check upzip dir whether exists, if not exists we create it
        File target = new File(destPath);
        if (!target.exists()) {
            if (!target.mkdirs()) {
                logger.info("cann't create file " + target.getName());
                return result;
            }
        }

        String mainFileName = zipFile.getName().replace(".zip", "");
        File targetFile = new File(destPath + File.separator + mainFileName);
        if (targetFile.exists()) {
            logger.info(targetFile.getName() + " already exist.");
            return result;
        }
        ZipInputStream zis = null;
        logger.info("start unzip file ...");
        try {
            FileInputStream fis = new FileInputStream(zipFile);
            CheckedInputStream checksum = new CheckedInputStream(fis, new Adler32());
            zis = new ZipInputStream(new BufferedInputStream(checksum));
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                int count;
                byte data[] = new byte[BUFFER];
                String entryName = entry.getName();

                // 处理中文乱码问题
                entryName = new String(entryName.getBytes("UTF-8"), "GBK");

                // logger.info(entryName);
                String newEntryName = destPath + File.separator + mainFileName + File.separator + entryName;
                File f = new File(newEntryName);
                if (newEntryName.endsWith(File.separator)) {
                    if (!f.exists()) {
                        if (!f.mkdirs()) {
                            throw new RuntimeException("can't create directory " + f.getName());
                        }
                    }
                } else {
                    File temp = f.getParentFile();
                    if (!temp.exists()) {
                        if (!temp.mkdirs()) {
                            throw new RuntimeException("create file " + temp.getName() + " fail");
                        }
                    }
                    FileOutputStream fos = new FileOutputStream(f);
                    BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                }
            }
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (result) {
            logger.info("done.");
        } else {
            logger.info("fail.");
        }
        return result;
    }

    private static FileInputStream fis = null;
    private static FileOutputStream fos = null;
    private static ZipOutputStream zipOut = null;
    private static String sourcepath = "C:\\Oracle\\Middleware\\user_projects\\domains\\HPSC_Producer_Sandbox\\globalResources\\portlet\\config\\deviceconfig";
    private static List<String> folderList = new ArrayList<String>(Arrays.asList(sourcepath));
    private static List<String> folderList2 = new ArrayList<String>(Arrays.asList("c:\\temp"
                                                                                  + File.separator
                                                                                  + sourcepath.substring(sourcepath.lastIndexOf(File.separator))));

    /**
     * Java实现Zip压缩目录中的所有文件自身压缩
     * 
     * @param args
     * @throws IOException
     */
    public static void zipFolderFiles() {
        for (int j = 0; j < folderList.size(); j++) {
            new File(folderList2.get(j)).mkdirs();
            String[] file = new File(folderList.get(j)).list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (folderList.get(j).endsWith(File.separator)) {
                    temp = new File(folderList.get(j), file[i]);
                } else {
                    temp = new File(folderList.get(j), file[i]);
                }
                File zipFile = new File(folderList2.get(j), temp.getName() + ".zip");
                if (temp.isFile() && !zipFile.exists())
                    try {
                        fis = new FileInputStream(temp);
                        fos = new FileOutputStream(zipFile);
                        zipOut = new ZipOutputStream(fos);
                        ZipEntry entry = new ZipEntry(temp.getName());
                        zipOut.putNextEntry(entry);
                        int nNumber;
                        byte[] buffer = new byte[20480];
                        while ((nNumber = fis.read(buffer)) != -1)
                            zipOut.write(buffer, 0, nNumber);
                        zipOut.flush();
                    } catch (IOException e) {
                        continue;
                    } finally {
                        try {
                            zipOut.close();
                            fos.close();
                            fis.close();
                        } catch (IOException e) {
                        }
                    }
                else if (temp.isDirectory()) {
                    folderList.add(folderList.get(j) + File.separator + file[i]);
                    folderList2.add(folderList2.get(j) + File.separator + file[i]);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {

        // 1. zip file by using speicfic zip package name for sepcific directory files

        // 2. upzip zip file to speicfic directory
        // String zipfile = "C:\\zkh\\Tool\\Book\\Maven.zip";
        // ZipUtil.unzip(new File(zipfile), "C:\\zkh\\Tool\\Book");

        // zipFile(new File("C:\\Users\\zhangkeh\\Downloads"));

        zipFolderFiles();

    }
}
