package catto.uwu.utils.authentication;

import catto.uwu.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HWID {

    public static void main(String[] args) {
        try {
            JOptionPane.showMessageDialog(null, "Your HWID: " + HWID.getComputerHWID(), "UwU Protection", JOptionPane.INFORMATION_MESSAGE);
            StringSelection stringSelection = new StringSelection(HWID.getComputerHWID());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

        } catch (Exception e) {
            Client.clientData.logError("Error while showing JPane", e);
        }
    }

    public static String getComputerHWID(){
        String hwid = null;
        try {
            hwid = getHwidNotSupported();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        /*if (OSHelper.getOS().equals(OSHelper.WINDOWS)){
            if(getWindowsMotherBoardSerialNumber().equalsIgnoreCase("NA")) {
                try {
                    hwid = getHwidNotSupported();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
                hwid = getWindowsMotherBoardSerialNumber();
        }else if (OSHelper.getOS().equals(OSHelper.LINUX)){
            hwid = getLinuxMotherBoardSerialNumber();
        }else if (OSHelper.getOS().equals(OSHelper.MAC)){
            try {
                hwid = getHwidNotSupported();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        return hwid;
    }

    private static String getLinuxMotherBoardSerialNumber() {
        String command = "dmidecode -s baseboard-serial-number";
        String sNum = null;
        try {
            Process SerNumProcess = Runtime.getRuntime().exec(command);
            BufferedReader sNumReader = new BufferedReader(new InputStreamReader(SerNumProcess.getInputStream()));
            sNum = sNumReader.readLine().trim();
            SerNumProcess.waitFor();
            sNumReader.close();
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
            sNum =null;
        }
        return sNum;
    }


    private static String getWindowsMotherBoardSerialNumber() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto",".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);

            String vbs =
                    "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                            + "Set colItems = objWMIService.ExecQuery _ \n"
                            + "   (\"Select * from Win32_BaseBoard\") \n"
                            + "For Each objItem in colItems \n"
                            + "    Wscript.Echo objItem.SerialNumber \n"
                            + "    exit for  ' do the first cpu only! \n"
                            + "Next \n";

            fw.write(vbs);
            fw.close();

            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        }
        catch(Exception E){
            System.err.println(E.getMessage());
        }
        return result.trim();
    }

    private static String getHwidNotSupported() throws Exception {
        String hi= System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME") + System.getProperty("user.name");
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] encodedhash = digest.digest(
                hi.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public enum OSHelper {
        WINDOWS("AppData" + File.separator + "Roaming" + File.separator + ".minecraft"),
        MAC("Library" + File.separator + "Applacation Support" + File.separator + "minecraft"),
        LINUX(".minecraft");

        private final String mc;

        OSHelper(String mc) {
            this.mc = File.separator + mc + File.separator;
        }

        public String getMc() {
            return String.valueOf(System.getProperty("user.home")) + this.mc;
        }

        public static final OSHelper getOS() {
            String currentOS = System.getProperty("os.name").toLowerCase();
            if (currentOS.startsWith("windows")) {
                return WINDOWS;
            }
            if (currentOS.startsWith("mac")) {
                return WINDOWS;
            }
            return LINUX;
        }
    }
}
