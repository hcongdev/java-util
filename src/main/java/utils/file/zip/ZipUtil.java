package utils.file.zip;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.Enumeration;


public class ZipUtil {
    /**
     * @param out  输出流
     * @param file 目标文件
     * @param dir  文件夹
     * @throws Exception
     */
    private static void generateFile(ZipOutputStream out, File file, String dir) throws Exception {

        // 当前的是文件夹，则进行一步处理
        if (file.isDirectory()) {
            //得到文件列表信息
            File[] files = file.listFiles();

            //将文件夹添加到下一级打包目录
            out.putNextEntry(new ZipEntry(dir + "/"));

            dir = dir.length() == 0 ? "" : dir + "/";

            //循环将文件夹中的文件打包
            for (int i = 0; i < files.length; i++) {
                generateFile(out, files[i], dir + files[i].getName());
            }

        } else { // 当前是文件

            // 输入流
            FileInputStream inputStream = new FileInputStream(file);
            // 标记要打包的条目
            out.putNextEntry(new ZipEntry(dir));
            // 进行写操作
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) > 0) {
                out.write(bytes, 0, len);
            }
            // 关闭输入流
            inputStream.close();
        }

    }

    /**
     * 递归压缩文件
     * @param output ZipOutputStream 对象流
     * @param file 压缩的目标文件流
     * @param childPath 条目目录
     */
    private static void zip(ZipOutputStream output,File file,String childPath){
        FileInputStream input = null;
        try {
            // 文件为目录
            if (file.isDirectory()) {
                // 得到当前目录里面的文件列表
                File list[] = file.listFiles();
                childPath = childPath + (childPath.length() == 0 ? "" : "/")
                        + file.getName();
                // 循环递归压缩每个文件
                for (File f : list) {
                    zip(output, f, childPath);
                }
            } else {
                // 压缩文件
                childPath = (childPath.length() == 0 ? "" : childPath + "/")
                        + file.getName();
                output.putNextEntry(new ZipEntry(childPath));
                input = new FileInputStream(file);
                int readLen = 0;
                byte[] buffer = new byte[1024 * 8];
                while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
                    output.write(buffer, 0, readLen);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 关闭流
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    /**
     * 压缩文件（文件夹）
     * @param path 目标文件流
     * @param format zip 格式 | rar 格式
     * @throws Exception
     */
    public static String zipFile(File path,String format) throws Exception {
        String generatePath = "";
        if( path.isDirectory() ){
            generatePath = path.getParent().endsWith("/") == false ? path.getParent() + File.separator + path.getName() + "." + format: path.getParent() + path.getName() + "." + format;
        }else {
            generatePath = path.getParent().endsWith("/") == false ? path.getParent() + File.separator : path.getParent();
            generatePath += path.getName().substring(0,path.getName().lastIndexOf(".")) + "." + format;
        }
        // 输出流
        FileOutputStream outputStream = new FileOutputStream( generatePath );
        // 压缩输出流
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(outputStream));
        zip(out, path,"");
        out.flush();
        out.close();
        return generatePath;
    }

    /**
     * 解压zip文件
     *
     * @param zipFilePath
     *            zip文件绝对路径
     * @param unzipDirectory
     *            解压到的目录
     * @throws Exception
     */
    public static String unzip(String zipFilePath, String unzipDirectory) throws Exception {
        unzipDirectory = zipFilePath.substring(0, zipFilePath.lastIndexOf("\\")) + "\\";
        // 定义输入输出流对象
        InputStream input = null;
        OutputStream output = null;
        String rpath = "";
        try {
            // 创建文件对象
            File file = new File(zipFilePath);
            // 创建zip文件对象
            ZipFile zipFile = new ZipFile(file);
            // 创建本zip文件解压目录
            String name = file.getName().substring(0, file.getName().lastIndexOf("."));
            // File unzipFile = new File(unzipDirectory + "/" + name);
            File unzipFile = new File(unzipDirectory);// 直接解压缩到指定目录，不创建文件夹
            if (unzipFile.exists()) {
                unzipFile.delete();
            }
            unzipFile.mkdir();
            // 得到zip文件条目枚举对象
            Enumeration zipEnum = zipFile.getEntries();
            // 定义对象
            ZipEntry entry = null;
            String entryName = null, path = null;
            String names[] = null;
            int length;
            // 循环读取条目
            while (zipEnum.hasMoreElements()) {
                // 得到当前条目
                entry = (ZipEntry) zipEnum.nextElement();
                entryName = new String(entry.getName());
                // 用/分隔条目名称
                names = entryName.split("\\/");
                length = names.length;
                path = unzipFile.getAbsolutePath();
                for (int v = 0; v < length; v++) {
                    if (v < length - 1) { // 最后一个目录之前的目录
                        createDirectory(path += "/" + names[v] + "/");
                    } else { // 最后一个
                        if (entryName.endsWith("/")) { // 为目录,则创建文件夹
                            createDirectory(unzipFile.getAbsolutePath() + "/" + entryName);
                        } else { // 为文件,则输出到文件
                            input = zipFile.getInputStream(entry);
                            String epath = unzipFile.getAbsolutePath() + "/" + entryName;
                            output = new FileOutputStream(new File(epath));
                            if (epath.contains(".txt")) {
                                rpath += epath + ";";
                            }
                            byte[] buffer = new byte[1024 * 8];
                            int readLen = 0;
                            while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1)
                                output.write(buffer, 0, readLen);
                        }
                    }
                }

            }
            return rpath;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 关闭流
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.flush();
                output.close();
            }
            return rpath;
        }
    }
    public static void createDirectory(String dir) {

        File directory = new File(dir);

        // director.mkdir()方法只能创建一级目录，其父级目录必须存在，否则会有异常
        if (directory.mkdir()) {
        } else {

            // 使用mkdirs()方法可以创建多层级目录
            if (directory.mkdirs()) {
            } else {
            }
        }
    }
}
