package com.dev.qlda.constant;

import java.util.Arrays;
import java.util.List;

public class AssetType {
    public static final String THIET_BI = "Thiết bị";
    public static final String CONG_TRINH = "Công trình";
    public static final String NOI_THAT = "Nội thất";
    public static final String PHU_TRO = "Phụ trợ";
    public static final String TAI_LIEU = "Tài liệu";
    public static final String THIET_BI_AN_NINH = "Thiết bị an ninh";
    public static final String THIET_BI_THE_THAO = "Thiết bị thể thao";

    private static final List<String> ALL_VALUES = Arrays.asList(
            THIET_BI,
            CONG_TRINH,
            NOI_THAT,
            PHU_TRO,
            TAI_LIEU,
            THIET_BI_AN_NINH,
            THIET_BI_THE_THAO
    );

    public static boolean isValueInConstants(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        return ALL_VALUES.contains(value.trim());
    }
}
