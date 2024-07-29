package br.com.wmw.components.core.domain.entity;

public class Core {

    private final String id;
    private final String url;
    private final String customerId;
    private final String clientUrl;

    public Core(String id, String url, String customerId, String clientUrl) {
        this.id = id;
        this.url = url;
        this.customerId = customerId;
        this.clientUrl = clientUrl;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    @Override
    public String toString() {
        return "Core [id=" + id + ", url=" + url + ", customerId=" + customerId + "]";
    }
}
