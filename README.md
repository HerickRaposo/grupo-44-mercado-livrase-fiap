1. Solicitação de Login
   O usuário faz uma solicitação de login fornecendo seu CPF e senha.

2. Verificação das Credenciais
   O Spring Security intercepta a solicitação de login, pois todas as solicitações passam pelo filtro de segurança (JwtAuthenticationFilter) configurado no SecurityConfig.
   O filtro JwtAuthenticationFilter encaminha a solicitação para o endpoint de login (/api/login), onde o usuário forneceu CPF e senha.
   O UsuarioService é invocado para autenticar o usuário. Aqui, o Spring Security não interfere diretamente, pois o UsuarioService é chamado internamente para verificar as credenciais do usuário.
3. Geração do Token JWT
   Se as credenciais do usuário estiverem corretas, o UsuarioService gera um token JWT para o usuário. O Spring Security não está diretamente envolvido nesse processo.
   O JwtTokenProvider é responsável por gerar o token JWT com base nas informações do usuário.
4. Retorno do Token JWT
   Após a geração do token JWT, ele é retornado como parte da resposta à solicitação de login.
   O usuário recebe o token JWT e o armazena localmente (geralmente em um cookie ou armazenamento local) para usar em solicitações subsequentes.
5. Acesso aos Endpoints Protegidos
   Para acessar os endpoints protegidos, o usuário deve incluir o token JWT nas solicitações.
   O Spring Security intercepta todas as solicitações para os endpoints protegidos.
   O filtro JwtAuthenticationFilter verifica a presença do token JWT em cada solicitação.
   Se o token JWT for válido e não estiver expirado, o usuário é considerado autenticado e autorizado a acessar o recurso solicitado.
   Se o token JWT for inválido ou expirado, o acesso é negado e uma resposta de erro é retornada.
   Todas essas verificações são configuradas no SecurityConfig.
   Configuração do Spring Security (SecurityConfig)
   O SecurityConfig configura o Spring Security para lidar com a segurança da aplicação. Ele define as regras de autorização para os endpoints e configura os filtros de segurança necessários.

No método configure(HttpSecurity http), especificamos as regras de autorização para os endpoints da aplicação. Aqui, permitimos acesso público ao endpoint de login (/api/login) e exigimos autenticação para todos os outros endpoints. Também configuramos o ponto de entrada para tratamento de exceções de autenticação e especificamos que não queremos criar sessões de usuário.

Configuramos o filtro de autenticação JWT (JwtAuthenticationFilter) para ser aplicado antes do filtro padrão de autenticação com usuário e senha. Isso garante que todas as solicitações sejam verificadas quanto à presença e validade do token JWT antes de serem processadas pelo Spring Security.

No método configure(AuthenticationManagerBuilder auth), configuramos o AuthenticationManagerBuilder para usar o UsuarioService como fonte de autenticação. Isso permite que o Spring Security autentique os usuários com base nas informações fornecidas pelo UsuarioService.

O SecurityConfig trabalha em conjunto com outras classes e componentes do Spring Security para garantir que a aplicação esteja protegida contra acessos não autorizados e que os usuários tenham acesso apenas aos recursos permitidos.