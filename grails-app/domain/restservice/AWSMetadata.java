package restservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jpdixon on 4/29/16.
 */
public final class AWSMetadata {
    private static final AWSMetadata instance=null;
    private String region;

	public String getAmi_id() {
		return (String) metadataMap.get("ami-id");
	}

	public void setAmi_id(String ami_id) {
		this.ami_id = ami_id;
	}

	private String ami_id;
    private String ami_launch_index;
    private String availability_zone;

	public String getHostname() {
		return (String) metadataMap.get("hostname");
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	private String hostname;
    private String local_hostname;
    private String instance_id;
    private String instance_type;
    private String public_hostname;
    private String public_ipv4;
    private String reservation_id;

	private Map metadataMap=new HashMap();

    protected AWSMetadata() {
        // defeat constructor

        // call the metadata service and populate the region
        this.getAWSMetaData();
    }

    // singleton pattern
    public static final AWSMetadata getInstance() {
        if(instance==null) return new AWSMetadata();

        return(instance);
    }

	public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }

	private String populateMetadata(String key) {
		String metadataValue="";
		String urlString="http://169.254.169.254/latest/meta-data/" + key;
		URL url;
		InputStream is=null;
		BufferedReader br;
		String line;

		try {
			url=new URL(urlString);

			URLConnection urlCxn=url.openConnection();
			urlCxn.setConnectTimeout(15000);
			urlCxn.setReadTimeout(15000);
			urlCxn.setAllowUserInteraction(false);
			urlCxn.setDoOutput(true);

			is=urlCxn.getInputStream();
			br= new BufferedReader(new InputStreamReader(is));

			while((line=br.readLine()) !=null) {
				metadataValue=line;
			}
		}
		catch(SocketTimeoutException ste) {
			// if we got here, the metadata service must not be responding (we are on something other than an AWS EC2 instance)
			this.setRegion("localhost");
		}
		catch(java.net.ConnectException ce) {
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

		return(metadataValue);
	} // populateMetadata

    private void getAWSMetaData() {
        metadataMap.put("ami-id","NO_AMI");
		metadataMap.put("ami-launch-index","NO_AMI_LAUNCH_INDEX");
		metadataMap.put("hostname","NO_HOSTNAME");
		metadataMap.put("placement/availability-zone","NO_AVAILABILITY_ZONE");

		// get the metadata for the ami-id key and put it into the map
		metadataMap.put("ami-id", this.populateMetadata("ami-id"));
		metadataMap.put("hostname", this.populateMetadata("hostname"));


		URL url;
        InputStream is=null;
        BufferedReader br;
        String line;

        try {
            //url=new URL("http://169.254.169.254/latest/meta-data/instance-id");
            url=new URL("http://169.254.169.254/latest/meta-data/placement/availability-zone");

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
        catch(java.net.ConnectException ce) {
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
