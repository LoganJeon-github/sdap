<template>
  <v-main style="margin: 2%">
    <v-dialog 
      width="auto"
      v-model="isCreateFolderDialog"
    >
    <!-- <template >
    </template> -->
    <v-card style=" width: 343px; height: 184px;" v-if="isCreateFolderDialog">
      <v-card-text>
        새 폴더
      </v-card-text>
      <v-text-field style="padding: 4px 20px 0px;"
        v-model="createFolderName">
      </v-text-field>
      <v-btn-group style="margin-bottom: 10px; margin-left: 10px;">
        <v-card-actions style="width: 64px;">
          <v-btn color="primary" block @click="createFolder">만들기</v-btn>
        </v-card-actions>
        <v-card-actions style="width: 64px;">
          <v-btn block @click="isCreateFolderDialog = false"> 취소</v-btn>
        </v-card-actions>
      </v-btn-group>
    </v-card>
    </v-dialog>
    
    <v-dialog 
      width="auto"
      v-model="isFileUploadDialog"
    >
    <!-- <template >
    </template> -->
    <v-card style=" width: 686px; height: 184px;" v-if="isFileUploadDialog">
      <v-card-text>
        파일 업로드
      </v-card-text>
      <v-file-input
        style="width: 650px; border-color: blue;"
        label="File input"
        variant="solo"
        @change="selectFile"
        id="excel" />

      <v-btn-group style="margin-bottom: 10px; margin-left: 10px;">
        <v-card-actions style="width: 64px;">
          <v-btn color="primary" block @click="submit">업로드</v-btn>
        </v-card-actions>
        <v-card-actions style="width: 64px;">
          <v-btn block @click="isFileUploadDialog = false"> 취소</v-btn>
        </v-card-actions>
      </v-btn-group>
    </v-card>
    </v-dialog>

    <aside style="margin-right: 20%;">
      <v-btn 
        elevation="0" 
        icon="mdi-folder-plus"
        @click="isCreateFolderDialog = !isCreateFolderDialog"
      ></v-btn>
      <v-btn elevation="0"
        icon="mdi-file-upload-outline"
        @click="isFileUploadDialog = !isFileUploadDialog">
      </v-btn>
      <a v-for="(dir, index) in currentDirectory" :key="dir">
        <v-btn elevation="0" @click="moveDirectory(index)">{{ dir.name }} / </v-btn>
      </a>
      <v-divider></v-divider>
      <v-table density="compact">
        <thead>
          <tr>
            <th >
              Name
            </th>
            <th>
              Created At
            </th>
            <th>
              Modified At
            </th>
          </tr>
        </thead>
        <!-- @click="enterFolder(folder.id)" -->

        <tbody>
          <tr 
            v-for="folder in folders" 
            :key="folder"
            @dblclick="enterFolder(folder.id)"
            
          >
            <!-- <v-btn elevation="0">
            </v-btn> -->
            <td>
              <v-btn class="btn_case" elevation="0" flat :background-color="colorsssssssss" >
                <v-icon v-show="folder.type == 0">mdi-folder</v-icon>
                <v-icon v-show="folder.type == 1">mdi-file</v-icon>
                {{ folder.name }}
              </v-btn>
            </td>
            <td>{{ folder.createdAt }}</td>
            <td>{{ folder.modifiedAt }}</td>
          </tr>
        </tbody>
      </v-table>
    </aside>
    
  </v-main>
</template>

<script>
import Axios from 'axios';
export default {
  data() {
    return {
      Error : false,
      currentDirectory: [{id:0, name:''}],
      folders: null,
      isCreateFolderDialog: false,
      createFolderName: "제목없는 폴더",
      isFileUploadDialog: false,
      selectedFile: null,
      colorsssssssss:"lightslategrey",
    }
  },
  computed:{
  },
  created() {
    this.setUpData('/folders/0');
  },
  methods: {
    setUpData: function(url){
      this.$axios.get(url).then((data) =>{
        this.folders = data.data;
        for( let i = 0 ; i < this.folders.length; ++i){
          this.folders[i].createdAt = this.dateFormatter(this.folders[i].createdAt);
          this.folders[i].modifiedAt = this.dateFormatter(this.folders[i].modifiedAt);
        }
        console.log(this.folders);
      })
    },
    async readCsv(id){
      var fileName = this.findFolderForId(id).name
      try {
        this.$axios.get('/test/read' + fileName).then((data)=>{
          console.log(data);

        })
      } catch (error) {
        console.log(error);
      }
    },
    enterFolder: function(id){
      /**
       * file type ==1 file
       * file type ==0 folder
       */
      if (this.findFolderForId(id).type == 1){
        var fileName = this.findFolderForId(id).name
        console.log(fileName);
        this.$axios.get('/test/read/' + fileName).then((data)=>{
          console.log(data.data);

        })
      }
      else{
        this.currentDirectory.push({id:this.findFolderForId(id).id,name:this.findFolderForId(id).name});
        this.$axios.get('/folders/'+id).then((data)=>{
          this.folders = data.data;
          for( let i = 0 ; i < this.folders.length; ++i){
            this.folders[i].createdAt = this.dateFormatter(this.folders[i].createdAt);
            this.folders[i].modifiedAt = this.dateFormatter(this.folders[i].modifiedAt);
          }
        })
      }
      
    },
    findFolderForId: function(id){
      for (let i = 0 ; i < this.folders.length; ++i){
        if (this.folders[i].id == id){
          return this.folders[i];
        }
      }
    },
    findFolderForName: function(name){
      for (let i = 0 ; i < this.folders.length; ++i){
        if (this.folders[i].name == name){
          console.log(this.folders[i].name)
          return this.folders[i];
        }
      }
    },
    findFolderForparent: function(parentId){
      for (let i = 0 ; i < this.folders.length; ++i){
        if (this.folders[i].parentId == parentId){
          return this.folders[i];
        }
      }
    },
    addItem: function(e){
      var value = e.target.value;
      console.log(value);
      if (value == ""){
        console.log("isEmpty");
        return false;
      }
      if (this.itemExist(value) == true){
        this.Error = true
        console.log("isExist");
        return false;
      }
      this.Error = false;
      var tempJson = {};
      tempJson.id = this.folders.length +1;
      tempJson.name = value;
      this.folders.push(tempJson);
      e.target.value = "";
    },
    itemExist: function(value){
      for (let i = 0 ; i < this.folders.length ;++i){
        if (this.folders[i].name == value){
          return true;
        }
      }
      return false;
    },
    removeItem: function (folderId) {
      for(let i = 0 ; i < this.folders.length ; ++i){
        if (this.folders[i].id == folderId){
          this.folders.splice(i,1);
        }
      }
    },
    moveDirectory: function(index) {
      var clickedDirectory = this.currentDirectory[index];
      console.log(clickedDirectory);

      var url = '/folders/' + clickedDirectory.id;
      console.log(clickedDirectory.id);
      this.setUpData(url);
      for(let i = this.currentDirectory.length ; i >= 0; --i){
        if (i > index){
          this.currentDirectory.splice(i, 1);
        }
      }
      
    },
    dateFormatter: function(date){
      var tempDate = new Date(date);
      var month = '0';
      var day = '0';
      if (parseInt(tempDate.getMonth() + 1) < 10){
        month += parseInt(tempDate.getMonth()+ 1).toString();
      }
      else{
        month = parseInt(tempDate.getMonth() + 1).toString();
      }
      if (parseInt(tempDate.getDate()) < 10) {
        day +=  tempDate.getDate();
      }
      else{
        day = tempDate.getDate();
      }
      var resultDate = tempDate.getFullYear() + '-' + month + '-' + day + ' '
                      + tempDate.getHours() + ':' + tempDate.getMinutes() + ':' + tempDate.getSeconds();
      return resultDate;
    },
    createFolder: function(){
      // console.log(this.createFolderName);
      if (this.findFolderForName(this.createFolderName)){
        // console.log('종뷁');
        this.isCreateFolderDialog = false;
        return;
      }
      // console.log(this.folders)
      
      var data = {}
      data.name = this.createFolderName
      data.parentId = this.folders[0].parentId
      this.$axios.post('/folders/add-folder',data).then((/**response*/)=>{
        // console.log(response);
        var url = 'folders/' + this.folders[0].parentId
        this.setUpData(url);
      });
      this.isCreateFolderDialog = false;
    },
    selectFile: function(){
      this.selectedFile = document.getElementById("excel").files[0];
      
    },
    async submit(){
      var frmData = new FormData();
      frmData.append('files', this.selectedFile);

      try {
        const {data} = await Axios.post('/test/upload', frmData, {
          header: {
            "Content-Type": 'multipart/form-data'
          },
          maxBodyLength: Infinity

        });
        console.log(data);
        this.setUpData('/folders/' + this.folders[0].parentId);

      } catch (error) {
        console.log(error);
      }
       
      this.isFileUploadDialog  = false;
    }
    
  },
}
</script>

<style>
.btn_case {
  text-transform: none !important;
}
</style>