package com.project.banco.repository



interface BancoRepository{
    fun save(conta: String )//aqui é tipo Conta mas coloquei string p não dar erro
    fun findById(idConta:Long)
    fun deleteById(idConta: Long)
    fun findAll()
    fun setSaldoAumentado(valor:Double, idConta: Long)
    fun setSaldoReduzido(valor:Double, idConta: Long)

}