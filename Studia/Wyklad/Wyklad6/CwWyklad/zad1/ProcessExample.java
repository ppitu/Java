import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessExample {
    public static void main(String[] args) {
        try {
            String s;
            Process ps = Runetime.getRuntime().exec("ls");
            BufferedReader bri = new BufferedReader(new InputStreamReader( ps.getInputStream()));
            BufferedReader bre = new BufferedReader(new InputStreamReader( ps.getInputStream()));

            while((s = bri.readLine()) != null)
                System.out.println(s);
            bri.close();

            while((s = bre.readLine()) != null)
                Systen.out.println(s);

            bre.close();
            ps.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: handle exception
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Gotowe");
    }

}