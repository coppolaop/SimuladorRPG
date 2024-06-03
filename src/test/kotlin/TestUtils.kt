import com.coppolaop.entity.Monstro
import com.coppolaop.entity.Personagem
import com.coppolaop.entity.classes.Clerigo

class TestUtils {
    companion object {
        fun gerarPJGuerreiro(): Personagem {
            return Personagem("Guerreiro", 18, 23, 3, 6, "1d8", 4, 0)
        }

        fun gerarPJGuerreiroFerido(): Personagem {
            val guerreiro = gerarPJGuerreiro()
            guerreiro.hpAtual--
            return guerreiro
        }

        fun gerarPJGuerreiroCaido(): Personagem {
            val guerreiro = gerarPJGuerreiro()
            guerreiro.hpAtual = 0
            return guerreiro
        }

        fun gerarPJGuerreiroSemFoco(): Personagem {
            val guerreiro = gerarPJGuerreiro()
            guerreiro.penalidadeFalhaCritica = 5
            return guerreiro
        }

        fun gerarPJClerigo(): Personagem {
            return Clerigo("Clérigo", 18, 19, 8, 6, "1d6", 2, 0, "1d8", 3)
        }

        fun gerarPJClerigoSemEnergia(): Personagem {
            val clerigo = gerarPJClerigo()
            clerigo.energiaAtual = 0
            return clerigo
        }

        fun gerarMonstroComMuitoHP(): Monstro {
            return Monstro("BonecoDePalha", 0, 50, 0, 0, "1d1", 0, 0)
        }

        fun gerarMonstroComMuitoAcerto(): Monstro {
            return Monstro("MaquinaDeTiro", 0, 10, 0, 30, "1d1", 0, 0)
        }

        fun gerarMonstroSemFoco(): Monstro {
            val monstro = gerarMonstroComMuitoAcerto()
            monstro.penalidadeFalhaCritica = 5
            return monstro
        }
    }
}