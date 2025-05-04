import java.util.*

val expressaoRegular = Regex("[0-5]")
val expressaoNome = Regex("^[A-Za-zÀ-ÿ\\s]+$")

var listaConvidados: MutableList<Convidado> = mutableListOf()

fun main() {
    val i = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    println(i)
    menu()
}

private fun menu() {
    var opcao: String
    do {
        println("--- MENU ---")
        println("1- CADASTRAR")
        println("2- LISTAR")
        println("3- EDITAR")
        println("4- EXCLUIR")
        println("5- BUSCA")
        println("0- SAIR")
        opcao = readln()

        if (expressaoRegular.matches(opcao)) {
            when (opcao.toInt()) {
                1 -> {
                    println("Cadastrando...")
                    cadastrar()
                }
                2 -> {
                    println("Listando...")
                    listar()
                }
                3 -> {
                    println("Editando...")
                    editar()
                }
                4 -> {
                    println("Excluindo...")
                    excluir()
                }
                5 -> {
                    println("Buscando...")
                    busca()
                }
                0 -> println("Saindo")
            }
        } else {
            println("\n\n\nOpção inválida")
        }

    } while (opcao != "0")
}

private fun cadastrar() {
    val convidado = Convidado()

    do {
        print("Qual o seu nome? ")
        convidado.nome = readln()
        if (!expressaoNome.matches(convidado.nome)) {
            println("Digite apenas letras.")
        }
    } while (!expressaoNome.matches(convidado.nome))

    print("Qual vai ser o presente? ")
    convidado.presente = readln()

    print("Qual sua restrição alimentar? ")
    convidado.alimentar = readln()

    listaConvidados.add(convidado)
}

private fun listar(): String {
    var i = 0
    listaConvidados.sortBy { it.nome }

    if (validar()) {
        listaConvidados.forEach { convidado ->
            println(
                "Posição: ${i++} | " +
                        "Nome: ${convidado.nome}; " +
                        "Presente: ${convidado.presente}; " +
                        "Restrição: ${convidado.alimentar}; " +
                        "Vai ir à festa? ${convidado.presenca}"
            )
        }
    }
    return "Listagem foi um sucesso"
}

private fun editar(): Boolean {
    if (validar()) {
        listar()

        var posicao: Int
        while (true) {
            println("Digite a posição a ser editada: ")
            val entrada = readln()
            if (entrada.matches(Regex("\\d+"))) {
                posicao = entrada.toInt()
                if (posicao in listaConvidados.indices) break
                else println("Posição fora da lista.")
            } else {
                println("Digite um número válido.")
            }
        }

        var resposta: String
        do {
            println("O convidado vai? (S/N)")
            resposta = readln().uppercase()
        } while (resposta != "S" && resposta != "N")

        listaConvidados[posicao].presenca = resposta == "S"
    }
    return true
}

private fun excluir(): Boolean {
    if (validar()) {
        listar()

        var posicao: Int
        while (true) {
            println("Qual posição você deseja remover: ")
            val entrada = readln()
            if (entrada.matches(Regex("\\d+"))) {
                posicao = entrada.toInt()
                if (posicao in listaConvidados.indices) break
                else println("Posição fora da lista.")
            } else {
                println("Digite um número válido.")
            }
        }

        listaConvidados.removeAt(posicao)
        println("Convidado excluído.")
    }
    return true
}

private fun busca() {
    print("Digite o nome da pessoa que você busca: ")
    val busca = readln()

    if (!expressaoNome.matches(busca)) {
        println("Digite apenas letras para buscar.")
        return
    }

    val termoBusca = busca.lowercase()
    var i = 0
    listaConvidados.forEach { convidado ->
        if (convidado.nome.lowercase().contains(termoBusca)) {
            println("Posição: $i, Nome: ${convidado.nome}")
        }
        i++
    }
}

private fun validar(): Boolean {
    if (listaConvidados.isEmpty()) {
        println("Lista vazia! Finalizando...")
        return false
    }
    return true
}

