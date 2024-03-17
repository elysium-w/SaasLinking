package org.saas.project.service;

/**
 * Url标题接口层
 */

public interface UrlTitleService {
    /**
     * 根据url获取网站标题
     * @param url url
     * @return 网站title
     */
    String getTitleFromUrl(String url);
}
