Vue.component("v-select", VueSelect.VueSelect)
Vue.component('updcuota-component', {
  props: {
    cuotaprop: {
      type: Object,
      default: null,
    },
    situacionprop: {
      type: Number,
      default: 0,
    }
  },
  template:
    `<transition name="modal">
            <div class="modal-mask">
              <div class="modal-wrapper">
                <div class="modal-container">
        
                  <div class="modal-header text-center">
                    <slot name="header">
                     <h3> {{ title }} </h3> 
                    </slot>
                  </div>
        
                  <div class="modal-body">
                    <slot name="body">
                            
                          <div class="card ">
                          <div class="card-body">
                            <form  @submit.prevent="handleSubmit()">
                              <div class="form-group row">
                                <label for="numRenovaciContrato" class="col-form-label col-sm-4">Renovacion</label>
                                <div class="col-sm-8">
                                  <input type="text" class="form-control form-control-sm" v-model.trim="cuota.numRenovaciContrato"
                                   v-validate="'required'"  name="numRenovaciContrato" :disabled="disabled">
                                   <div class="alert-danger container-error-message" v-if="errors.has('numRenovaciContrato')">El Campo es requerido</div>
                                </div>
                              </div>
                        
                              <div class="form-group row">
                                <label for="numCuota" class="col-form-label col-sm-4">Cuota</label>
                                <div class="col-sm-8">                                 
                                 <input type="number" class="form-control form-control-sm" v-model.trim="cuota.numCuota"
                                  v-validate="'required'"  name="numCuota" :disabled="disabled">
                                  <div class="alert-danger container-error-message" v-if="errors.has('numCuota')">La Cuota es requerido</div>
                                </div>
                              </div>
                              <div class="form-group row" > <!--v-if="situacion=== 2 || situacion=== 3"-->
                                <label for="fecInicioAcredi" class="col-form-label col-sm-4">Fecha de Inicio Cobertura</label>
                                <div class="col-sm-8">                                 
                                 <input type="date" class="form-control form-control-sm" v-model.trim="cuota.fecInicioAcredi"
                                   v-validate="'required'" name="fecInicioAcredi" >
                                  <div class="alert-danger container-error-message" v-if="errors.has('fecInicioAcredi')">La fecha es requerida</div>
                                </div>
                              </div>
                              <div class="form-group row" > <!-- v-if="situacion=== 2 || situacion=== 3"-->
                                <label for="fecFinAcredi" class="col-form-label col-sm-4">Fecha de Fin Cobertura</label>
                                <div class="col-sm-8">                                 
                                 <input type="date" class="form-control form-control-sm" v-model.trim="cuota.fecFinAcredi"
                                   v-validate="'required'" name="fecFinAcredi" >
                                  <div class="alert-danger container-error-message" v-if="errors.has('fecFinAcredi')">La fecha es requerida</div>
                                </div>
                              </div>
                              
                              <div class="form-group row" >
                                <label for="numMontoEssalud" class="col-form-label col-sm-4">Monto Essalud</label>
                                <div class="col-sm-8">
                                  <input type="number" class="form-control form-control-sm" v-model.trim="cuota.numMontoEssalud"
                                  v-validate="'required'"  name="numMontoEssalud">
                                  <div class="alert-danger container-error-message" v-if="errors.has('numMontoEssalud')">El Monto es requerido</div>
                                </div>
                              </div>
                        
                              <div class="form-group row" >
                                <label for="numMontoEvida" class="col-form-label col-sm-4">Monto +Vida</label>
                                <div class="col-sm-8">
                                  <input type="number" class="form-control form-control-sm" v-model.trim="cuota.numMontoEvida"
                                  v-validate="'required'"  name="numMontoEvida">
                                  <div class="alert-danger container-error-message" v-if="errors.has('numMontoEvida')">El Monto es requerido</div>
                                </div>
                              </div>
                              <!--
                              <div class="form-group row">
                                <label for="monMontoAporte" class="col-form-label col-sm-4">Monto Aporte</label>
                                <div class="col-sm-8">
                                  <input type="number" class="form-control form-control-sm" v-model.trim="cuota.monMontoAporte"
                                  v-validate="'required'" name="monMontoAporte" v-uppercase>
                                  <div class="alert-danger container-error-message" v-if="errors.has('monMontoAporte')">El Monto es requerido</div>
                                </div>
                              </div>    -->                       
                              <div class="form-group row">
                                <label for="fecVencimiePago" class="col-form-label col-sm-4">Fecha de Vencimiento</label>
                                <div class="col-sm-8">
                                  <input type="date" class="form-control form-control-sm" v-model.trim="cuota.fecVencimiePago"
                                   v-validate="'required'" name="fecVencimiePago" >
                                   <div class="alert-danger container-error-message" v-if="errors.has('fecVencimiePago')">La Fecha es requerido</div>
                                </div>
                              </div> 
                               <div class="form-group row" v-if="situacion=== 2">
                                <label for="fecAportePago" class="col-form-label col-sm-4">Fecha de Pago</label>
                                <div class="col-sm-8">                                 
                                 <input type="date" class="form-control form-control-sm" v-model.trim="cuota.fecAportePago"
                                   v-validate="'required'" name="fecAportePago" >
                                  <div class="alert-danger container-error-message" v-if="errors.has('fecAportePago')">El campo es requerido</div>
                                </div>
                              </div>
                               <div class="form-group row" v-if="situacion=== 2 || situacion=== 3">
                                <label for="codEstadoAporte" class="col-form-label col-sm-4">Estado Aporte</label>
                                <div class="col-sm-8">                                 
                                 <input type="text" class="form-control form-control-sm" v-model.trim="cuota.codEstadoAporte"
                                   v-validate="'required'"  name="codEstadoAporte">
                                  <div class="alert-danger container-error-message" v-if="errors.has('codEstadoAporte')">El campo es requerido</div>
                                </div>
                              </div> 
                              <div class="form-group row" v-if="situacion=== 2 ">
                                <label for="codAgenciaBancaria" class="col-form-label col-sm-4">Banco</label>
                                <div class="col-sm-8">
                                    <v-select 
                                        :options="bancos"
                                        label="txtDescripcCorto"
                                        v-model="selected_document"                                        
                                    ></v-select>
                                </div>
                                <div class="alert-danger container-error-message" v-if="errors.has('codAgenciaBancaria')">El campo es requerido</div>
                                <!--<label for="codAgenciaBancaria" class="col-form-label col-sm-4">Banco</label>
                                <div class="col-sm-8"> 
                                    <select v-model="banco.codElementoTabla" class="form-control form-control-sm"  name="codAgenciaBancaria">
                                        <option v-for="banco in bancos" >{{banco.txtDescripcCorto}}</option>
                                    </select>-->
                                 <!--<select v-validate="'required'"  name="codAgenciaBancaria">
                                    <option></option>
                                 </select>                                 
                                </div>-->
                              </div>
                              <div class="form-group row" v-if="situacion=== 2">
                                <label for="numOperacioBancaria" class="col-form-label col-sm-4">Número Operación</label>
                                <div class="col-sm-8">                                 
                                 <input type="text" class="form-control form-control-sm" v-model.trim="cuota.numOperacioBancaria"
                                   v-validate="'required'"  name="numOperacioBancaria">
                                  <div class="alert-danger container-error-message" v-if="errors.has('numOperacioBancaria')">El campo es requerido</div>
                                </div>
                              </div>
                              <!--<div class="form-group row" v-if="situacion=== 2">
                                <label for="munMontoNeto" class="col-form-label col-sm-4">Monto Pago Banco</label>
                                <div class="col-sm-8">                                 
                                 <input type="text" class="form-control form-control-sm" v-model.trim="cuota.munMontoNeto"
                                   v-validate="'required'"  name="munMontoNeto">
                                  <div class="alert-danger container-error-message" v-if="errors.has('munMontoNeto')">El campo es requerido</div>
                                </div>
                              </div>-->
                              <div class="form-group row">
                                <div class="col-sm-12 text-right">
                                  <button class="btn btn-primary mr-2" type="submit">Grabar</button>   
                                  <button class="btn btn-danger" type="button" @click="$emit('close')">Salir</button>                   
                                </div>
                              </div>
                            </form>
                        
                          </div>
                        </div>
                    </slot>
                  </div>
                </div>
              </div>
            </div>
          </transition>
     `,
  data: () => ({
    title: "",
    cuota: {
      numRenovaciContrato: "",
      numCuota: "",
      numMontoEvida: "",
      monMontoAporte: "",
      fecVencimiePago: "",
      numContratoInscripc: "",
      fecInicioAcredi: "",
      fecFinAcredi: "",
      fecAportePago: "",
      codEstadoAporte: "",
      correlativoRegistroComplemen: ""
    },
    actualizar: false,
    situacion: 0,
    bancos: [],
    selected_document: {
      codElementoTabla: "",
      txtDescripcCorto: ""
    },
    disabled: false
  }),
  mounted() {
    if (this.situacionprop === 1) {
      this.cuota.numContratoInscripc = this.cuotaprop.numContratoInscripc;
      this.title = "Ingresar Nueva Cuota";
      console.log("nueva cuota 17.02");

      /*            this.cuota.numRenovaciContrato='';
                  this.cuota.numCuota='';
                  this.cuota.numMontoEssalud='0';
                  this.cuota.numMontoEvida='0';
                  this.cuota.fecVencimiePago=null;*/

    } else if (this.situacionprop === 2) {
      this.cuota = this.cuotaprop;
      this.title = "Ingresar Pagos";
      this.disabled = true;

      axios.get(`/pagosapp/api/pagos/bancos`)
        .then(function (response) {
          console.log("response.data", response.data);
          this.bancos = response.data;
        }.bind(this))
        .catch(error => {
          console.log(error);
          if (error.response.status === 302 || error.response.status === 403) {
            window.location.href = '/authoriza';
          } else {
            swal({
              type: 'error',
              title: 'Error',
              text: 'Ocurrio un error inesperado',
            });
          }
        });
      console.log("bancos33", this.bancos);
    } else {
      this.cuota = this.cuotaprop;
      this.title = "Actualizar Cuota";
      this.disabled = true;
    }
    this.situacion = this.situacionprop;

    if (this.cuotaprop.fecVencimiePago) {
      console.log("fec-ve", this.cuotaprop.fecVencimiePago);
      const fecVencimiePago = moment(this.cuotaprop.fecVencimiePago, 'YYYYMMDD').format('YYYY-MM-DD');
      if (fecVencimiePago !== 'Invalid date') {
        this.cuotaprop.fecVencimiePago = fecVencimiePago;
      }

      //const fecInicioAcredi = '';//moment(this.cuotaprop.fecInicioAcredi,'YYYYMMDD').format('YYYY-MM-DD');
      //const fecFinAcredi = '';// moment(this.cuotaprop.fecFinAcredi,'YYYYMMDD').format('YYYY-MM-DD');

      const fecInicioAcredi = moment(this.cuotaprop.fecInicioAcredi, 'YYYYMMDD').format('YYYY-MM-DD');
      const fecFinAcredi = moment(this.cuotaprop.fecFinAcredi, 'YYYYMMDD').format('YYYY-MM-DD');
      const fecAportePago = moment(this.cuotaprop.fecAportePago, 'YYYYMMDD').format('YYYY-MM-DD');

      if (fecInicioAcredi !== 'Invalid date') {
        this.cuotaprop.fecInicioAcredi = fecInicioAcredi;
      }
      if (fecFinAcredi !== 'Invalid date') {
        this.cuotaprop.fecFinAcredi = fecFinAcredi;
      }
      if (fecAportePago !== 'Invalid date') {
        this.cuotaprop.fecAportePago = fecAportePago;
      }
      this.cuota = this.cuotaprop;
      this.actualizar = true
    }
  },
  methods: {
    handleSubmit() {
      this.$validator.validateAll().then(() => {
        let txtSwal = "Esta Seguro de Actualizar el Cronograma?";
        let urlApi = "/pagosapp/api/pagos/cuota";
        let methodApi = "post";
        let cuota = this.cuota;
        this.cuota.fecVencimiePago = this.formatDate(this.cuota.fecVencimiePago);
        this.cuota.fecInicioAcredi = this.formatDate(this.cuota.fecInicioAcredi);
        this.cuota.fecFinAcredi = this.formatDate(this.cuota.fecFinAcredi);
        if (this.situacion == "1") {
          //console.log("cuota33:",this.cuota)
          txtSwal = "Esta Seguro de registrar nueva cuota?";
          urlApi = "/pagosapp/api/pagos/nuevacuota";
          methodApi = "post";
          cuota = this.cuota;
        } else if (this.situacion == "2") {
          this.cuota.codAgenciaBancaria = this.selected_document.codElementoTabla;
          console.log("cuota33:", this.cuota);
          console.log("sel33:", this.selected_document);
          txtSwal = "Esta Seguro de ingresar pago?";
          urlApi = "/pagosapp/api/pagos/actpago";
          methodApi = "put";
          cuota = this.cuota;
        } else if (this.situacion == "3") {
          console.log("cuota33:", this.cuota)
          txtSwal = "Esta Seguro de Actualizar la cuota?";
          urlApi = "/pagosapp/api/pagos/actcuota";
          methodApi = "put";
          cuota = this.cuota;
        }
        console.log("situacionrfv", this.situacion);
        //console.log("situacionrfv",result.situacion);
        swal({
          type: 'question',
          title: 'Cronograma de Pagos',
          text: txtSwal,
          showCancelButton: true,
          confirmButtonColor: '#005286',
          cancelButtonColor: '#d33',
          confirmButtonText: 'Aceptar',
          cancelButtonText: 'Cancelar'
        }).then(function (result) {
          if (result.value) {
            //console.log("cuota25",cuota);
            //console.log("thiscuota25",cuota);
            swal({
              title: 'Procesando..., por favor espere',
              showConfirmButton: false,
            });
            axios({
              method: methodApi,
              url: urlApi,
              data: cuota
            })
              .then(function (response) {
                if (response.data.codigo == '1') {
                  swal({
                    type: 'success',
                    title: 'Cronograma de Pagos',
                    text: response.data.mensaje,
                    confirmButtonColor: '#005286',
                    confirmButtonText: 'Aceptar',
                  }).then(function (result) {
                    if (result.value) {
                      app2.refCronograma();
                      app2.showModal = false;
                    }
                  })
                } else {
                  swal({
                    type: 'error',
                    title: 'Cronograma de Pagos',
                    text: response.data.mensaje,
                    confirmButtonColor: '#005286',
                    confirmButtonText: 'Aceptar',
                  })
                }
                //this.$emit('close');
                //this.$emit('refresh');
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
          }
        }).catch(error => {
          console.log(error)
        });

      }).catch(error => {
        console.log(error)
      });
    },
    formatDate(date) {
      if (date) {
        return moment(String(date)).format('YYYYMMDD')
      }
    },
    showValor() {
      console.log("ingreso al metodo");
    }

  },
});
