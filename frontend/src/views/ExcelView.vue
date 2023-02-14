<template>
  <main style="margin: 2%;"> 
    <div class="about">
      <v-file-input
        label="File input"
        variant="solo"
        @change="readFile"
      >
      </v-file-input>
      <!-- <vue-excel-editor
        v-if="jsonData != null"
        v-model="jsonData"
        :height="excelHeight"
        no-mouse-scroll
        width="200%"
      >
      </vue-excel-editor> -->
    </div>
  </main>
</template>

<script>
import * as XLSX from "xlsx";

export default {
  data() {
    return {
      inputData: null,
      result: null,
      jsonData: null,
      keyS: null, 
      valuesMaxLength : null,
      excelHeight: "",
    }
  },
  setup() {
  },
  created() {
    
  },
  methods: {
    readFile(event) {
      const file = event.target.files[0];
      const reader = new FileReader();
      let tmpResult = {};
      reader.onload = e => {
        let data = e.target.result;
        data = new Uint8Array(data);
        let excelFile = XLSX.read(data, { type: "array" });
        // console.log(excelFile);
        excelFile.SheetNames.forEach(function (sheetName) {
          const roa = XLSX.utils.sheet_to_json(excelFile.Sheets[sheetName], {
            header: 1,
          });
          if (roa.length) tmpResult[sheetName] = roa;
        });
        this.result = tmpResult.Sheet1;
        this.jsonData = this.makeJson(this.result);
        this.excelHeight = (50 + this.result.length * 20).toString() + "px";
        // console.log(this.excelHeight);
        // console.log(this.result.length.toString());
      };
      reader.readAsArrayBuffer(file);
    },
    makeJson(param) {
      let keys = param[0];
      this.keyS = keys;
      let values = [];
      for (let i = 1; i < param.length; ++i) {
        values.push(param[i]);
      }
      // make json
      let tempJson = [];
      for (let i = 0; i < values.length; ++i) {
        let json = {};
        for (let j = 0; j < values[i].length; ++j) {
          json[keys[j]] = values[i][j];
        }
        tempJson.push(json);
        // console.log(i);
      }
      // console.log(tempJson);
      console.log('json 변환 끝');
      return tempJson;
    },
  }
}
</script>

<style>
.about {
  height: 200;
}
</style>