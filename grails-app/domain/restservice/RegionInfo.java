package restservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jpdixon on 4/29/16.
 */
public class RegionInfo {
    private static RegionInfo instance=null;
    private String region;

    protected RegionInfo() {
        // defeat constructor

        // call the metadata service and populate the region
        this.getAWSRegionInfo();
    }

    // singleton pattern
    public static final RegionInfo getInstance() {
        if(instance==null) return new RegionInfo();

        return(instance);
    }
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    private void getAWSRegionInfo() {
        URL url;
        InputStream is=null;
        BufferedReader br;
        String line;

        try {
            url=new URL("http://169.254.169.254/latest/meta-data/instance-id");
            URLConnection urlCxn=url.openConnection();
            urlCxn.setConnectTimeout(15000);
            urlCxn.setReadTimeout(15000);
            urlCxn.setAllowUserInteraction(false);
            urlCxn.setDoOutput(true);

            is=urlCxn.getInputStream();
            br= new BufferedReader(new InputStreamReader(is));

            while((line=br.readLine()) !=null) {
                this.setRegion(line);

            }
        }
        catch(SocketTimeoutException ste) {
            // if we got here, the metadata service must not be responding (we are on something other than an AWS EC2 instance)
            this.setRegion("localhost");
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                if (is != null) is.close();
            }
            catch (IOException ioe) {
                // noop
            }

        } // catch
        finally {

        }


    }

} // class
