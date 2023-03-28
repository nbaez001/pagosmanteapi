Vue.component('cronograma-component', {
    template:
        ` <div>
                <div class="table100 ver1 m-b-110">
                    <div class="table100-head">
                        <table>
                            <thead>
                                <tr class="row100 head">
                                    <th class="cell100 column1"># Renvación-Cuota</th>
                                    <th class="cell100 column2">Cobertura Desde</th>
                                    <th class="cell100 column3">Cobertura Hasta</th>                                    
                                    <th class="cell100 column4">Monto Aporte</th>
                                    <th class="cell100 column5">Fec Vencimiento</th>
                                    <th class="cell100 column6">Fec Pago</th>                                    
                                    <th class="cell100 column7">Estado</th>
                                    <th class="cell100 column8">Acciones</th>
                                </tr>
                             </thead>
                        </table>
                     </div>
                        
                     <div class="table100-body js-pscroll">
                         <table>
                            <tbody>                                            
                                <tr class="row100 body" v-for="cronograma in cronogramas">
                                <td class="cell100 column1">{{cronograma.numRenovaciContrato}} - {{cronograma.numCuota}}</td>
                                <td class="cell100 column2">{{cronograma.fecInicioAcredi | formatDate}}</td>
                                <td class="cell100 column3">{{cronograma.fecFinAcredi | formatDate}}</td>
                                <td class="cell100 column4">{{cronograma.monMontoAporte }}</td>
                                <td class="cell100 column5">{{cronograma.fecVencimiePago | formatDate}}</td>
                                <td class="cell100 column6">{{cronograma.fecAportePago }}</td>
                                <td class="cell100 column7">{{cronograma.codEstadoAporte}}</td>
                                <td class="cell100 column8">
                                    <div style="display: flex; align-items: center;">                                         
                                            <a href="javascript:" @click.prevent="cargarPago(cronograma.numRenovaciContrato)">
                                                <i class="fas fa-lg fa-info-circle" style="padding-left: 16px ;padding-right: 8px" title="Ingresar Pago"></i>
                                            </a>
                                            <a href="javascript:" @click.prevent="updateCuota(cronograma)">
                                                <i class="fas fa-lg fa-edit" title="Actualizar Cuota"></i>
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
        cronogramas: [],
        parPage: 10,
        currentPage: 0,
        totalPaginas: 0,
        cronograma: null,
        contrato: "",
        fecVencimiento: ""
    }),
    mounted() {
        console.log('mounted');
    },
    methods: {
        fetchCronograPaginatedFilter(contrato, fecVencimiento, page) {
            console.log('fetchCronograPaginatedFilter');
            if (contrato.trim() === "") {
                swal({
                    type: 'warning',
                    title: 'Validación',
                    text: 'Ingrese el nro. de Contrato',
                });
                return
            }
            this.contrato = contrato;
            this.fecVencimiento = fecVencimiento;
            if (fecVencimiento.trim() === "") fecVencimiento = 'null';
            axios.get(`/pagosapp/api/pagos/filtrar/${contrato}/${fecVencimiento}/${page}`)
                .then(function (response) {
                    this.cronogramas = response.data.content;
                    this.totalPaginas = response.data.totalPages;
                    // this.$emit('showGridCronograma', true);
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
            this.fetchCronograPaginatedFilter(this.contrato, this.fecVencimiento, this.currentPage - 1)

        },
        updateCuota(compendio) {
            //this.$emit('load-compendio', compendio);
        },
        cargarPago(e) {
            //  sessionStorage.setItem("pagina",this.currentPage)
            // document.location.replace(`/apicompendios/detalles/${e}`)
        }
    }

});