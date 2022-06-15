package com.pea.service.masterservice.entity.watchlist;

public interface AppendSql {

    default String replaceSpace(String param) {
        return param.replaceAll("[^a-zA-Z0-9ก-๙]", "").toUpperCase();
    }

    default String sql(boolean isFirst, Integer code) {
        if (isFirst) {
            return " WHERE ";
        } else {
            switch (code) {
                case 1:
                    return " AND ";
                case 2:
                    return " OR ";
                default:
                    return " AND ";
            }
        }
    }
}
