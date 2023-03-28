Vue.component('cronograma-component', {
    template:
        ` <div>
                <div class="table100 ver1 m-b-110">
                    <div class="table100-head">
                        <table>
                            <thead>
                                <tr class="row100 head">
                                    <th class="cell100 column1"># Renovación-Cuota</th>
                                    <th class="cell100 column2">Cobertura Desde</th>
                                    <th class="cell100 column3">Cobertura Hasta</th>                                    
                                    <th class="cell100 column4">Monto Aporte</th>
                                    <th class="cell100 column5">Cod. Banco</th>
                                    <th class="cell100 column6">Monto Pago</th>
                                    <th class="cell100 column7">Num. Operac.</th>
                                    <th class="cell100 column8">Monto +Vida</th>
                                    <th class="cell100 column9">Fec Vencimiento</th>
                                    <th class="cell100 column10">Fec Pago</th>                                    
                                    <th class="cell100 column11">Estado</th>
                                    <th class="cell100 column12">Acciones</th>
                                </tr>
                             </thead>
                        </table>
                     </div>
                        
                     <div class="table100-body js-pscroll">
                         <table>
                            <tbody>                                            
                                <tr class="row100 body" v-for="cuota in cuotas">
                                <td class="cell100 column1">{{cuota.numRenovaciContrato}} - {{cuota.numCuota}}</td>
                                <td class="cell100 column2">{{cuota.fecInicioAcredi | formatDate}}</td>
                                <td class="cell100 column3">{{cuota.fecFinAcredi | formatDate}}</td>
                                <td class="cell100 column4">{{cuota.numMontoEssalud }}</td>
                                <td class="cell100 column5">{{cuota.codAgenciaBancaria }}</td>
                                <td class="cell100 column6">{{cuota.munMontoNeto }}</td>
                                <td class="cell100 column7">{{cuota.numOperacioBancaria }}</td>
                                <td class="cell100 column8">{{cuota.numMontoEvida }}</td>
                                <td class="cell100 column9">{{cuota.fecVencimiePago | formatDate}}</td>
                                <td class="cell100 column10">{{cuota.fecAportePago | formatDate }}</td>
                                <td class="cell100 column11">{{cuota.codEstadoAporte}}</td>
                                <td class="cell100 column12">
                                    <div style="display: flex; align-items: center; margin-right: 8px">                                         
                                            <a v-if="cuota.codEstadoAporte === 'C'" href="javascript:" style="padding-right: 18px;" @click.prevent="actualizarCuota(cuota, 2)">
                                                Ing. Pago
                                            </a>
                                            <a v-if="cuota.codEstadoAporte === 'C'"  href="javascript:" style="padding-right: 18px;" @click.prevent="actualizarCuota(cuota, 3)">
                                                Act. Cuota
                                            </a>    
                                            <a  v-if="cuota.codEstadoAporte === 'A'"  href="javascript:" style="padding-right: 18px;" @click.prevent="cambiarEstado(cuota)">
                                                Anular Pago
                                            </a>
                                            <a  href="javascript:" @click.prevent="eliminarCuota(cuota)">
                                                Eliminar cuota
                                            </a>
                                              
                                    </div>
                                </td>
                            
                                </tr>
                            </tbody>
                          </table>
                    </div>
                </div>
    
                <span class="pull-right">
                    <paginate
                        :page-count="totalPaginas"
                        :page-range="3"
                        :margin-pages="2"
                        :click-handler="clickCallback"
                        :prev-text="'＜'"
                        :next-text="'＞'"
                        :container-class="'pagination'"
                        :page-class="'page-item'"
                        v-model="currentPage"
                        v-if="totalPaginas>1">
                    </paginate>
                 </span>
            </div>
     `,
    data: () => ({
        cuotas: [],
        parPage: 10,
        currentPage: 0,
        totalPaginas: 0,
        cuota: null,
        contrato: "",
        fecVencimiento: "",
        estado: ""
    }),
    mounted() {
        console.log('mounted');
        console.log("this.cuotas-0", this.cuotas);
    },
    methods: {
        fetchCronograPaginatedFilter(contrato, fecVencimiento, estado, page) {
            if (contrato.trim() === "") {
                swal({
                    type: 'warning',
                    title: 'Validación',
                    text: 'Ingrese el número de Contrato',
                });
                return
            }
            this.contrato = contrato;

            //VALIDANDO QUE SOLO SE PERMITAN CONTRATOS 11 Y 091
            let codContrato = this.contrato.substr(0, 2);
            console.log("codContrato", codContrato);

            if (codContrato != '11') {
                codContrato = this.contrato.substr(0, 3);
                if (codContrato != '091') {
                    swal({
                        type: 'warning',
                        title: 'Validación',
                        text: 'Solo es permitido los contratos que inician en 091 o 11',
                    });
                    return
                }
            }

            this.fecVencimiento = fecVencimiento;
            if (fecVencimiento.trim() === "") {
                fecVencimiento = 'null';
            }
            this.estado = estado;
            if (estado.trim() === "") {
                estado = 'null';
            }
            axios.get(`/pagosapp/api/pagos/filtrar/${contrato}/${fecVencimiento}/${estado}/${page}`)
                .then(function (response) {
                    this.cuotas = response.data.content;
                    this.totalPaginas = response.data.totalPages;
                    this.visualizarTipoSeguro(contrato);
                    console.log("this.cuotas", this.cuotas);
                    console.log("longitud: ", this.cuotas.length);
                    if (this.cuotas.length == 0) {
                        swal({
                            type: 'warning',
                            title: 'Cronograma',
                            text: 'No se encontraron resultados para los valores ingresados',
                        });
                        return
                    }

                }.bind(this))
                .catch(function (error) {
                    if (error.response.status === 302 || error.response.status === 403) {
                        window.location.href = '/authoriza';
                    } else {
                        swal({
                            type: 'error',
                            title: 'Error',
                            text: 'Ocurrio un error inesperado',
                        });
                    }
                })
        },
        clickCallback: function (pageNum) {
            this.currentPage = Number(pageNum);
            this.fetchCronograPaginatedFilter(this.contrato, this.fecVencimiento, this.estado, this.currentPage - 1)

        },
        actualizarCuota(cuota, situacion) {
            console.log('kkkk', situacion);
            this.$emit('actualizar-cuota', { cuota: cuota, situacion: situacion });
        },
        cambiarEstado(cuota) {
            console.log("cambiarEstado", cuota);
            this.$emit('cambiar-estado', cuota);
        },
        eliminarCuota(cuota) {
            console.log("eliminarCuota", cuota);
            this.$emit('eliminar-cuota', cuota);
        },
        visualizarTipoSeguro(contrato) {
            axios.get(`/pagosapp/api/pagos/tipo-seguro/${contrato}`)
                .then(function (response) {
                    const seguro = response.data.tipoSeguo;
                    this.$emit('load-seguro', seguro);
                }.bind(this)).catch(error => {
                    this.$emit('load-seguro', 'No se encontro Tipo de Seguro');
                    if (error.response.status === 302 || error.response.status === 403) {
                        window.location.href = '/authoriza';
                    } else {
                        swal({
                            type: 'error',
                            title: 'Error',
                            text: 'Ocurrio un error inesperado',
                        });
                    }
                })
        }
    }

});
