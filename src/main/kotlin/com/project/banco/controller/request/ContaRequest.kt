package com.project.banco.controller.request

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ContaRequest(val idConta:Long,
                        var saldo:Double
)  {
}