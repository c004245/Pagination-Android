package hyunwook.co.kr.paginationrecycler;

import com.google.gson.annotations.SerializedName;

public class PostItem {

    @SerializedName("description")
    private String mDesc;

    @SerializedName("time")
    private String mTime;

    @SerializedName("title")
    private String mTitle;

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
