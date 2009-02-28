package biz.pinedesk.slimtimer;

class LoginResponse {
    private int userId;
    private String accessToken;

    LoginResponse(int userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
