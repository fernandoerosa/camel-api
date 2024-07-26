package br.com.wmw.components.auth.domain.services;

public interface IAuthService {
    public String generateToken(String email, String coreUrl);
    public boolean authenticate(String email, String password);
}
