export const authConfig = {
    clientId: 'oauth2-pkce-client',
    //authorizationEndpoint: 'http://localhost:8181/realms/fitness-app/protocol/openid-connect/auth',
    //tokenEndpoint: 'http://localhost:8181/realms/fitness-app/protocol/openid-connect/token',
    //redirectUri: 'http://localhost:5173',

    authorizationEndpoint:'https://keycloak-txe2.onrender.com/realms/fitness-oauth2/protocol/openid-connect/auth',
    tokenEndpoint:'https://keycloak-txe2.onrender.com/realms/fitness-oauth2/protocol/openid-connect/token',
    redirectUri: 'https://fitlexa.netlify.app',
    //redirectUri: 'http://localhost:5173',

    // authorizationEndpoint:'https://keycloak-26-5-2-fkun.onrender.com/realms/fitness-oauth2/protocol/openid-connect/auth',
    // tokenEndpoint:'https://keycloak-26-5-2-fkun.onrender.com/realms/fitness-oauth2/protocol/openid-connect/token',
    // redirectUri: 'http://localhost:5173',

    scope: 'openid profile email offline_access',
    autoLogin: false,
    onRefreshTokenExpire: (event) => event.logIn(),
} 
