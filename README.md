<p align="center">
    <img src="./assets/img/SimuladorRPG.png" width="400" alt="logo"/>
</p>
<h1 align="center">SimuladorRPG</h1>

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/solar.png)

![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white)

[![GitHub license](https://img.shields.io/github/license/coppolaop/CarecaokeAPI.svg)](https://github.com/coppolaop/https://img.shields.io/github/license/CarecaokeAPI.svg/blob/main/LICENSE)

## 📋 Tabela de conteúdo

1. 🎲️ [Sobre SimuladorRPG](#about)
2. 👾 [O que é essa ferramenta?](#what-is-this-tool)
3. ⚖️ [Funcionalidades](#features)
4. 🔨 [Desenvolvimento e teste unitário](#dev-and-tst)
5. 🌿 [Variáveis de ambiente](#env-variables)
6. 🐙 [GitHub Actions](#github-actions)
7. ©️ [Licença](#license)
8. ❤️ [Contribuidores](#contributors)

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/solar.png)

## <a name="about">🎲️ Sobre</a>

Alguns sistemas modernos de RPG de mesa possuem em seu cerne a proposta de combates equilibrados.
Muitos fatores são levados em conta nesse equilíbrio, mas abstraindo o encontro à ataques e curas (como infelizmente a
maioria dos jogadores faz), o combate não passa de estatística e matemática.
A partir desse ponto de vista, esta aplicação foi desenvolvida com intuito de auxiliar autores de sistemas, e mestres, a
equilibrar monstros para seus desafios de RPG. Entenda que este programa não está incentivando seu jogo
a ser resumido a ataques e defesas, mas te dando ferramentas para mensurar o quão difícil um desafio pode ser,
levando em conta apenas as estatísticas.

> Mestre, grandes monstros têm grandes mitos.<br/>
> Além de deixarem pistas de sua presença no entorno,<br/>
> permitindo uma boa antecipação em sua narração,<br/>
> é possivel que, com uma boa investigação,<br/>
> o grupo encontre informações preciosas<br/>
> antes de lidar com eles.<br/>
> -- O Caminho do Mestre Cafeinado (Vol 1) - Rafael Balbi

E como saber o quão perigoso um monstro realmente é? </br>
É neste ponto que entra esta aplicação!

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/solar.png)

## <a name="what-is-this-tool">👾 O que é essa ferramenta?</a>

SimuladorRPG é um simulador de combates de RPG, que recebe a ficha de um monstro,
o nível desejado dos personagens, a amostragem de execução e alguns parâmetros de regras utilizadas na mesa.

É uma aplicação desktop **Backend** para te ajudar a preparar desafios.

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/solar.png)

## <a name="features">⚖️ Funcionalidades</a>

- Simulação de rolagens de dado;
- Comparação de rolagens de acerto contra Classes de Armadura dos alvos;
- Sistema de ações;
- Definição de nível do grupo;
- Emulação opcional de ataques críticos em acertos maiores que superem a Classe de armadura do alvo em 10 pontos;
- Emulação opcional de três ações por turno;

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/solar.png)

## <a name="dev-and-tst">🔨 Desenvolvimento e teste unitário

Este projeto utiliza a linguagem Kotlin.
Para mais informações sobre esta tecnologia, consulte o site: https://kotlinlang.org.
Utilize sua IDE de preferência para o desenvolvimento.
Para os testes unitários, nossa meta é de tentarmos ter a cobertura máxima,
levando em conta nossas regras de negócio. Para executá-los, utilize sua IDE.

### Running the application in dev mode

A aplicação é desktop e por enquanto sua execução é em prompt de comando.
Apenas abra o arquivo Main.kt e execute-o.

### Related Guides

- [Kotlin documentation](https://kotlinlang.org/docs/getting-started.html)

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/solar.png)

## <a name="env-variables">🌿 Variáveis de ambiente</a>

Até o presente momento, não utilizamos variávies de ambiente na aplicação,
mas os arquivos de execução precisam estar na pasta
**resources (/src/main/resources)**. Uma pasta deve ser criada para armazenas os monstros, deu nome deve ser: "/pdm".
Para criação do arquivo, tome como base
este [arquivo utilizado no teste unitário](https://github.com/coppolaop/SimuladorRPG/blob/main/src/test/resources/pdm/gnoll.json).</br>
Existem algumas configurações que são feitas no main da aplicação, antes da chamada do simulador, elas são:

|            Name             |                                                                          Description                                                                          |
|:---------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| `SimuladorService("X", Y)`  | Realiza a configuração inicial do simulador, onde X é o nome do arquivo onde o monstro está e Y é a quantidade desejada de monstros daquele tipo na simulação |
|    `.ativarAcaoTripla()`    |                                                            Ativa o sistema de três ações por turno                                                            |
| `.ativarCriticoEmMais10()`  |                           Ativa a funcionalidade de acerto crítico em acertos que superem a Classe de Armadura do alvo em 10 pontos                           |
|     `.definirNivel(X)`      |                                            Define o nível dos personagens na simulação, onde X é o nível desejado                                             |
| `.obterTaxasDeVitoriaPJ(X)` |      Inicia a simulação, onde X é a amostragem desejada, o número de combates que será realizado. Para valores mais precisos, utilize números mais altos      |

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/solar.png)

## <a name="github-actions">🐙 GitHub Actions</a>

Este projeto ainda não utiliza **GitHub Actions** para automatizar tarefas, mas pretendemos utilizar.

## <a name="license">©️ Licença</a>

Este projeto está sobre a licença [Licença Apache, Versão 2.0](https://opensource.org/license/apache-2-0).

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/solar.png)

## <a name="contributors">❤️ Contribuidores</a>

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<a href="https://github.com/coppolaop"><img src="https://avatars.githubusercontent.com/u/19476398?v=4" width="100" height="100" alt=""/></a>
<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->