<a href="https://aimeos.org/">
    <img src="https://i.ibb.co/4SkShF8/mstile-150x150.png" alt="iBicos" title="iBicos" align="right" height="60" />
</a>

# iBicos backend


Bem vindo, este é o repositório back-end da plataforma  iBicos. O repositório do front-end está disponível [aqui](https://github.com/Classificantes-DH/ibicos-frontend).

## Ideia :bulb:

A plataforma visa integrar pessoas prestadores de serviços autônomos com potenciais cliente via anúncios, assim a ideia inicial ter a plataforma como uma intermediária na comunicação cliente-prestador.

###  Pré-requisitos
- JDK 11
- Maven
- MySQL

### Como rodar a aplicação
Configure as seguintes variáveis de ambiente:
`IBICOS_EMAIL_USERNAME #Usado para autenticação no servidor SMPT`
`IBICOS_EMAIL_PASSWORD #Usado para autenticação no servidor SMPT`
`IBICOS_SIGNING_JWT_KEY #Chave de assinatura para tokens JWT`

Execute os seguintes comandos na linha comando:
`git clone git@github.com:MaikHenriqueSP/ibicos-backend.git #Clonar repositório`
`mvn spring-boot:run `

**Obs**: Como a aplicação faz uso de variáveis ambiente para rodar, talvez seja necessário configurar sua IDE para que consiga capturar os valores, sendo então a linha de comando a maneira mais rápida de se ter a aplicação de pé.

## Contribuindo

Sinta-se à vontade para contribuir com o projeto e ajudá-lo ficar ainda melhor. Para tal basta apenas seguir as instruções:

1. Fork o projeto
2. Crie uma *branch*  (`git checkout -b nova-feature`)
3. "Commite" suas mudanças (`git commit -m 'feat(context): briefly explain your feature'`)
4. Faça um push na sua *branch* (`git push origin nova-feature`)
5. Abra uma  *Pull Request*
