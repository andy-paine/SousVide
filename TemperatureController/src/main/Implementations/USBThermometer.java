package main.Implementations;

import main.Interfaces.ITemperatureProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andy on 05/10/15.
 */
public class USBThermometer implements ITemperatureProvider {

    @Override
    public Double getTemperature() {
        try {
            ProcessBuilder builder = new ProcessBuilder("/home/andy/temperature.sh");
            builder.redirectErrorStream(true);
            Process process = builder.start();

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));
            String temp = "";
            String s;
            while ((s = stdInput.readLine()) != null)
                temp += s;

            String pattern = "\\S+(?=\\u00B0C)";

            Pattern patt = Pattern.compile(pattern);
            Matcher m = patt.matcher(temp);
            Double temperature = null;
            if (m.find())
                temperature = Double.valueOf(m.group(0));
            return temperature;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
