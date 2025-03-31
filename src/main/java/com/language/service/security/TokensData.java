package com.language.service.security;

public class TokensData {
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpSeconds;
    private long refreshTokenTokenExpSeconds;
    private String accessTokenId;
    private String refreshTokenId;

    private TokensData() {
    }

    private TokensData(String accessToken, String refreshToken, long accessTokenExpSeconds, long refreshTokenTokenExpSeconds, String accessTokenId, String refreshTokenId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpSeconds = accessTokenExpSeconds;
        this.refreshTokenTokenExpSeconds = refreshTokenTokenExpSeconds;
        this.accessTokenId = accessTokenId;
        this.refreshTokenId = refreshTokenId;
    }

    public static class Builder {
        private TokensData data;

        public Builder() {
            data = new TokensData();
        }

        public Builder accessToken(String accessToken) {
            data.setAccessToken(accessToken);
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            data.setRefreshToken(refreshToken);
            return this;
        }

        public Builder accessTokenExpSeconds(long accessTokenExpSeconds) {
            data.setAccessTokenExpSeconds(accessTokenExpSeconds);
            return this;
        }

        public Builder refreshTokenTokenExpSeconds(long refreshTokenTokenExpSeconds) {
            data.setRefreshTokenTokenExpSeconds(refreshTokenTokenExpSeconds);
            return this;
        }

        public Builder accessTokenId(String accessTokenId) {
            data.setAccessTokenId(accessTokenId);
            return this;
        }

        public Builder refreshTokenId(String refreshTokenId) {
            data.setRefreshTokenId(refreshTokenId);
            return this;
        }

        public TokensData build() {
            validate();
            return data;
        }

        private void validate() {
            if (data.getAccessToken() == null) {
                throw new IllegalStateException("Access token is null");
            }
            if (data.getAccessTokenId() == null) {
                throw new IllegalStateException("Access token ID is null");
            }
            if (data.getRefreshToken() == null) {
                throw new IllegalStateException("Refresh token is null");
            }
            if (data.getRefreshTokenId() == null) {
                throw new IllegalStateException("Refresh token ID is null");
            }
            if (data.getAccessTokenExpSeconds() <= 0) {
                throw new IllegalStateException("Access token lifetime must be greater than 0");
            }
            if (data.getRefreshTokenTokenExpSeconds() <= 0) {
                throw new IllegalStateException("Access token lifetime must be greater than 0");
            }
        }
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getAccessTokenExpSeconds() {
        return accessTokenExpSeconds;
    }

    public void setAccessTokenExpSeconds(long accessTokenExpSeconds) {
        this.accessTokenExpSeconds = accessTokenExpSeconds;
    }

    public long getRefreshTokenTokenExpSeconds() {
        return refreshTokenTokenExpSeconds;
    }

    public void setRefreshTokenTokenExpSeconds(long refreshTokenTokenExpSeconds) {
        this.refreshTokenTokenExpSeconds = refreshTokenTokenExpSeconds;
    }

    public String getAccessTokenId() {
        return accessTokenId;
    }

    public void setAccessTokenId(String accessTokenId) {
        this.accessTokenId = accessTokenId;
    }

    public String getRefreshTokenId() {
        return refreshTokenId;
    }

    public void setRefreshTokenId(String refreshTokenId) {
        this.refreshTokenId = refreshTokenId;
    }
}
