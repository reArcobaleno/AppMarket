package com.android.sugar.appmarket.http.protocol;

import com.android.sugar.appmarket.bean.AppInfo;
import com.android.sugar.appmarket.utils.LogUtils;
import com.android.sugar.appmarket.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: DetailProtocol
 * @Description:
 * @author: SugarT
 * @date: 16/7/25 上午10:49
 */
public class DetailProtocol extends BaseProtocol<AppInfo> {

    private String mPackageName = "";

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

    @Override
    protected String getKey() {
        return "detail";
    }

    @Override
    protected String getParames() {
        if (StringUtils.isEmpty(mPackageName)) {
            return super.getParames();
        } else {
            return "&packageName=" + mPackageName;
        }
    }

    @Override
    protected AppInfo parseFromJson(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            AppInfo info = new AppInfo();
            info.setId(obj.getLong("id"));
            info.setName(obj.getString("name"));
            info.setPackageName(obj.getString("packageName"));
            info.setIconUrl(obj.getString("iconUrl"));
            info.setStars(Float.valueOf(obj.getString("stars")));
            info.setDownloadNum(obj.getString("downloadNum"));
            info.setVersion(obj.getString("version"));
            info.setDate(obj.getString("date"));
            info.setSize(obj.getLong("size"));
            info.setDownloadUrl(obj.getString("downloadUrl"));
            info.setDes(obj.getString("des"));
            info.setAuthor(obj.getString("author"));

            JSONArray array = obj.getJSONArray("screen");
            List<String> screens = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                String screen = array.getString(i);
                screens.add(screen);
            }
            info.setScreen(screens);

            array = obj.getJSONArray("safe");
            List<String> safeUrlList = new ArrayList<>();
            List<String> safeDesUrlList = new ArrayList<>();
            List<String> safeDesList = new ArrayList<>();
            List<Integer> safeDesColorList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String safeUrl = object.getString("safeUrl");
                String safeDesUrl = object.getString("safeDesUrl");
                String safeDes = object.getString("safeDes");
                Integer safeDesColor = object.getInt("safeDesColor");

                safeUrlList.add(safeUrl);
                safeDesUrlList.add(safeDesUrl);
                safeDesList.add(safeDes);
                safeDesColorList.add(safeDesColor);
            }
            info.setSafeUrl(safeUrlList);
            info.setSafeDesUrl(safeDesUrlList);
            info.setSafeDes(safeDesList);
            info.setSafeDesColor(safeDesColorList);
            return info;
        } catch (Exception e) {
            LogUtils.e(e);
            return null;
        }
    }
}
