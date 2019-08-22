package examples.aaronhoskins.com.datapersistancedemo.model.phone;

import android.os.Parcel;
import android.os.Parcelable;

public class Phone implements Parcelable {
    private String phoneBrand;
    private String phoneSize;
    private String phoneModel;
    private String osType;
    private String id;

    public Phone() {
    }

    public Phone(String phoneBrand, String phoneSize, String phoneModel, String osType, String id) {
        this.phoneBrand = phoneBrand;
        this.phoneSize = phoneSize;
        this.phoneModel = phoneModel;
        this.osType = osType;
        this.id = id;
    }

    protected Phone(Parcel in) {
        phoneBrand = in.readString();
        phoneSize = in.readString();
        phoneModel = in.readString();
        osType = in.readString();
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phoneBrand);
        dest.writeString(phoneSize);
        dest.writeString(phoneModel);
        dest.writeString(osType);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Phone> CREATOR = new Creator<Phone>() {
        @Override
        public Phone createFromParcel(Parcel in) {
            return new Phone(in);
        }

        @Override
        public Phone[] newArray(int size) {
            return new Phone[size];
        }
    };

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    public String getPhoneSize() {
        return phoneSize;
    }

    public void setPhoneSize(String phoneSize) {
        this.phoneSize = phoneSize;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
