package com.hdh.android.mail.base.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/4 14:39
 */

public class ScoreDTO {

    /**
     *
     *   "updatedAt":1507863362000,
     "id":"171001152724036972",
     "others":"default",
     "status":1,
     "createdAt":1506842844000,
     "state":1,
     "value":2126800811,
     "role":1,
     "type":3
     */
    @SerializedName("status")
    public int status;
    @SerializedName("value")
    public long value;
    @SerializedName("type")
    public int type;
    @SerializedName("role")
    public int role;
}
