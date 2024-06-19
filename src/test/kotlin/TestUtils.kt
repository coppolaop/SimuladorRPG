import com.coppolaop.entity.Monstro
import com.coppolaop.entity.Personagem
import com.coppolaop.entity.classes.Clerigo
import com.coppolaop.entity.classes.Guerreiro
import com.coppolaop.entity.classes.Ladino
import com.coppolaop.entity.classes.Mago

class TestUtils {
    companion object {
        fun gerarPJSemClasse(): Personagem {
            return Personagem("Aventureiro", 18, 21, 3, 6, "1d8", 4, 0)
        }

        fun gerarPJGuerreiro(): Guerreiro {
            return Guerreiro("Guerreiro", 18, 21, 6, "1d8", 4, 0)
        }

        fun gerarPJGuerreiroFerido(): Guerreiro {
            val guerreiro = gerarPJGuerreiro()
            guerreiro.hpAtual--
            return guerreiro
        }

        fun gerarPJGuerreiroCaido(): Guerreiro {
            val guerreiro = gerarPJGuerreiro()
            guerreiro.hpAtual = 0
            return guerreiro
        }

        fun gerarPJGuerreiroSemFoco(): Guerreiro {
            val guerreiro = gerarPJGuerreiro()
            guerreiro.penalidadeFalhaCritica = 5
            return guerreiro
        }

        fun gerarPJClerigo(): Clerigo {
            return Clerigo("Clérigo", 18, 18, 8, 6, "1d6", 2, 0, "1d8", 3)
        }

        fun gerarPJClerigoSemEnergia(): Clerigo {
            val clerigo = gerarPJClerigo()
            clerigo.energiaAtual = 0
            return clerigo
        }

        fun gerarPJLadino(): Ladino {
            return Ladino("Ladino", 16, 15, 6, "1d4", 4, 4)
        }

        fun gerarPJMago(): Mago {
            return Mago("Mago", 12, 12, 10, 6, "1d6", 3, 2)
        }

        fun gerarMonstroComMuitoHP(): Monstro {
            return Monstro("BonecoDePalha", 0, 50, 0, 0, "1d1", 0, 0)
        }

        fun gerarMonstroComMuitoAcerto(): Monstro {
            return Monstro("MaquinaDeTiro", 0, 10, 0, 30, "1d1", 0, 0)
        }

        fun gerarMonstroComMuitaCA(): Monstro {
            return Monstro("AlvoMovelExtremamenteVeloz", 30, 50, 0, 0, "1d1", 0, 0)
        }

        fun gerarMonstroExtremamentePoderoso(): Monstro {
            return Monstro("PersonagemMaisFracoDeQualquerAnimeShonen", 10000, 10000, 10000, 10000, "1d1", 10000, 10000)
        }

        fun gerarMonstroSemFoco(): Monstro {
            val monstro = gerarMonstroComMuitoAcerto()
            monstro.penalidadeFalhaCritica = 5
            return monstro
        }
    }
}