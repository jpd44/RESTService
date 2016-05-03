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
	private String hostname;
	private String local_hostname;
	private String instance_id;
	private String instance_type;
	private String public_hostname;
	private String public_ipv4;
	private String reservation_id;
	private String ami_id;
	private String ami_launch_index;
	private String placement_availability_zone;

	public String getInstance_id() {
		return instance_id;
	}

	private void setInstance_id(String instance_id) {
		this.instance_id = instance_id;
	}

	public String getInstance_type() {
		return instance_type;
	}

	private void setInstance_type(String instance_type) {
		this.instance_type = instance_type;
	}

	public String getPublic_hostname() {
		return public_hostname;
	}

	private void setPublic_hostname(String public_hostname) {
		this.public_hostname = public_hostname;
	}

	public String getPublic_ipv4() {
		return public_ipv4;
	}

	private void setPublic_ipv4(String public_ipv4) {
		this.public_ipv4 = public_ipv4;
	}

	public String getReservation_id() {
		return reservation_id;
	}

	private void setReservation_id(String reservation_id) {
		this.reservation_id = reservation_id;
	}

	public String getAmi_launch_index() {
		return ami_launch_index;
	}

	private void setAmi_launch_index(String ami_launch_index) {
		this.ami_launch_index = ami_launch_index;
	}

	public String getPlacement_availability_zone() {
		return placement_availability_zone;
	}

	private void setPlacement_availability_zone(String placement_availability_zone) {
		this.placement_availability_zone = placement_availability_zone;
	}

	public String getLocal_hostname() {
		return local_hostname;
	}

	private void setLocal_hostname(String local_hostname) {
		this.local_hostname = local_hostname;
	}

	public String getAmi_id() {
		return ami_id;
	}

	private void setAmi_id(String ami_id) {
		this.ami_id = ami_id;
	}

	public String getHostname() {
		return hostname;
	}

	private void setHostname(String hostname) {
		this.hostname = hostname;
	}

    protected AWSMetadata() {
        // defeat constructor

        // get metadata values for each value in this object
        this.setAmi_id(getMetadataValue("ami-id"));
		this.setAmi_launch_index(getMetadataValue("ami-launch-index"));
		this.setHostname(getMetadataValue("hostname"));
		this.setInstance_id(getMetadataValue("instance-id"));
		this.setInstance_type(getMetadataValue("instance-type"));
		this.setLocal_hostname(getMetadataValue("local-hostname"));
		this.setPlacement_availability_zone(getMetadataValue("placement/availability-zone"));
		this.setPublic_hostname(getMetadataValue("public-hostname"));
		this.setPublic_ipv4(getMetadataValue("public-ipv4"));
		this.setReservation_id(getMetadataValue("reservation-id"));
	}

    // singleton pattern
    public static final AWSMetadata getInstance() {
        if(instance==null) return new AWSMetadata();

        return(instance);
    }

	private String getMetadataValue(String metadataKey) {
		String metadataValue="";

		// this is the base URL of the local metadata service on AWS instances
		String urlString="http://169.254.169.254/latest/meta-data/" + metadataKey;
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
			metadataValue="(n/a, localhost)";
		}
		catch(java.net.ConnectException ce) {
			// if we got here, the metadata service must not be responding (we are on something other than an AWS EC2 instance)
			metadataValue="(n/a, localhost)";
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
	} // getMetadataValue


} // class AWSMetadata
