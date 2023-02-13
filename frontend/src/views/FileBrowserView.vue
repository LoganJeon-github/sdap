<template>
  <v-main style="margin: 2%">
    <v-dialog 
      width="auto"
      v-model="isCreateFolderDialog"
    >
    <template v-slot:activator="{ props }">
      <v-btn
        color="primary"
        v-bind="props"
      >
        Open Dialog
      </v-btn>
    </template>
    </v-dialog>
    
    
    <aside style="margin-right: 20%;">
      <!-- <a v-if="currentDirectory.length >=  4"></a> -->
      <v-btn 
        elevation="0" 
        icon="mdi-folder-plus"
        @click="isCreateFolderDialog = !isCreateFolderDialog"
      ></v-btn>
      <a v-for="(dir, index) in currentDirectory" :key="dir">
        <v-btn elevation="0" @click="moveDirectory(index)">{{ dir.name }} / </v-btn>
      </a>
      <v-divider></v-divider>
      <v-table density="compact">
        <thead>
          <tr>
            <th>
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
        <tbody>
          <tr 
            v-for="folder in folders" 
            :key="folder"
            @click="enterFolder(folder.id)"
          >
            <!-- <v-btn elevation="0">
            </v-btn> -->
            
            <v-btn elevation="0">
              <v-icon v-if="folder.type == 0">mdi-folder</v-icon>
              <v-icon v-if="folder.type == 1">mdi-file</v-icon>
              {{ folder.name }}
            </v-btn>
            <v-divider></v-divider>
            <td >{{ folder.createdAt }}</td>
            <td>{{ folder.modifiedAt }}</td>
            <v-divider></v-divider>

            <!-- <v-btn elevation="0">{{ folder.name }}</v-btn>
            <td>{{ folder.createdAt }}</td>
            <td>{{ folder.modifiedAt }}</td> -->
          </tr>
        </tbody>
      </v-table>
    </aside>
    <v-card v-if="isCreateFolderDialog">
      <v-card-text>
        Hello Dialog
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary"  block @click="isCreateFolderDialog = false"> Close Dialog</v-btn>
      </v-card-actions>
    </v-card>
  </v-main>
</template>

<script>

export default {
  data() {
    return {
      Error : false,
      currentDirectory: [{id:null, name:''}],
      folders: null,
      isCreateFolderDialog: false,
    }
  },
  computed:{
  },
  created() {
    this.setUpData('/folders/65534');
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
    enterFolder: function(id){
      if (this.findFolderForId(id).type == 1){
        return;
      }
      this.currentDirectory.push({id:this.findFolderForId(id).id,name:this.findFolderForId(id).name});
      this.$axios.get('/folders/'+id).then((data)=>{
        this.folders = data.data;
        for( let i = 0 ; i < this.folders.length; ++i){
          this.folders[i].createdAt = this.dateFormatter(this.folders[i].createdAt);
          this.folders[i].modifiedAt = this.dateFormatter(this.folders[i].modifiedAt);
        }
      })
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

      var url = '/folders/';
      console.log(clickedDirectory.id);
      if (clickedDirectory.id == null){
        url += '65534';
      }
      else{
        url += (clickedDirectory.id);
      }
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
        console.log(tempDate.getDate());
      }
      else{
        day = tempDate.getDate();
      }
      var resultDate = tempDate.getFullYear() + '-' + month + '-' + day + ' '
                      + tempDate.getHours() + ':' + tempDate.getMinutes() + ':' + tempDate.getSeconds();
      return resultDate;
    },
    
  },
}
</script>

<style>

</style>