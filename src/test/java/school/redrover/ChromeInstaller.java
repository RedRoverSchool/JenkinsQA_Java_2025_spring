package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChromeInstaller {

    public static void installChrome() {
        try {
            // Установите Chrome, если он еще не установлен
            Process process = Runtime.getRuntime().exec(new String[]{
                "sh", "-c", "if ! command -v google-chrome > /dev/null; then " +
                "wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | sudo apt-key add - && " +
                "sudo sh -c 'echo \"deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main\" >> /etc/apt/sources.list.d/google-chrome.list' && " +
                "sudo apt-get update && sudo apt-get install -y google-chrome-stable; fi"
            });
            process.waitFor();

            // Убедитесь, что Chrome установлен
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Установите chromedriver с помощью WebDriverManager
            WebDriverManager.chromedriver().setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}