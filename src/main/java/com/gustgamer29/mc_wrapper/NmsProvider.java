package com.gustgamer29.mc_wrapper;

import com.gustgamer29.exception.UnSupportedVersion;
import com.gustgamer29.mc_wrapper.v1_8_R1.VersionControl1_8_R1;
import com.gustgamer29.mc_wrapper.v1_8_R2.VersionControl1_8_R2;
import com.gustgamer29.mc_wrapper.v1_8_R3.VersionControl1_8_R3;

public class NmsProvider {

    private String version;
    private NmsVersion nmsVersion;

    /**
     *
     * @param version craftbukkit version
     */
    public NmsProvider(String version) throws UnSupportedVersion {
        this.version = version;
        configure();
    }

    private void configure() throws UnSupportedVersion {
        ImplementedVersions v = ImplementedVersions.valueOf(this.version);
        switch (v){
            case v1_8_R1:
                this.nmsVersion = VersionControl1_8_R1.getInstance();
                break;
            case v1_8_R2:
                this.nmsVersion = VersionControl1_8_R2.getInstance();
                break;
            case v1_8_R3:
                this.nmsVersion = VersionControl1_8_R3.getInstance();
                break;
            default:
                throw new UnSupportedVersion("Your server is running with a version that isn't supported by this plugin yet!");
        }
    }

    public NmsVersion getNmsVersion(){
        return this.nmsVersion;
    }

}
