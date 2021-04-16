var vm = new Vue({
    el:"#app",
    data:{
        stulist:[],
        glist:[],
        //刚进来页面的时候，stuBean为空，但是在页面里面有 stuBean.gradeBean.gname的这个操作，因为stuBean都直接为空
        //在空里面  gradeBean相当于null，他进行操作了，相当于后台的空指针错误，初始化的时候，把里面的对象也要初始化
        stuBean:{gradeBean:{}},
        proList:[],
        cityList:[],
        couList:[],
        courseList:[],
        stuVo:{cids:{}},
        searchEntity:{yema:0,pageSize:5},
        page:{}
    },
    methods:{
        paging:function(yema){
            this.searchEntity.yema=yema;
            this.findStuList();
        },
        findStuList:function(){
            var _this = this;
            var url ="findStuList.do";
            axios.post(url,_this.searchEntity).then(function (response) {
                _this.stulist=response.data.stuList;
                _this.page = response.data.page;
            }).catch(function (err) {
                console.log(err)
            });
        },
        saveStuCourse:function(){
            var ck = document.getElementsByName("ck");
            var arr=[];
            for (var i = 0; i < ck.length; i++) {
                if(ck[i].checked){
                    arr.push(ck[i].value);
                }
            }
            this.stuVo.cids=arr;
            var _this = this;
            var url ="saveStuCourse.do";
            axios.post(url,_this.stuVo).then(function (response) {
                if(response.data.success){

                    document.getElementById("a4").style.display="none";
                }else{
                    alert(response.data.message);
                }
            }).catch(function (err) {
                console.log(err)
            });
        },
        getStuCourse:function(sid){
            var url="getStuCourse.do?sid="+sid;
            var _this = this;
            axios.get(url).then(function (response) {
                _this.stuVo = response.data;
                document.getElementById("a4").style.display="block";
                var cids = _this.stuVo.cids;
                //判断  学生vo里面的学生已经选择的课程不为空的时候，证明学生原来是有课程的
                if(cids!=null){
                    //获取页面的课程列表，和学生选择好的课程的id比对，相同的让选中
                    //获取页面的课程列表
                    var ck = document.getElementsByName("ck");
                    //先把复选框原来选中的全部取消，全部取消不知道用那个属性，只能遍历一个一个取消
                    for (var i = 0; i <ck.length ; i++) {
                        ck[i].checked="";
                    }
                    for (var x = 0; x < cids.length; x++) {
                        for (var y = 0; y < ck.length; y++) {
                            if(cids[x]==ck[y].value){
                                ck[y].checked="checked";
                                break;
                            }
                        }
                    }

                }
            }).catch(function (err) {
                console.log(err)
            });
        },
        getCourseList:function(){
            var url="getCourseList.do";
            var _this = this;
            axios.get(url).then(function (response) {
                _this.courseList = response.data;
            }).catch(function (err) {
                console.log(err)
            });
        },
        getCityName:function(arr,id){
            if(arr!=null){
                for (var i = 0; i < arr.length; i++) {
                    if(arr[i].id==id){
                        return arr[i].cname;
                    }
                }
            }
        },
        changecou:function(event){
            var id = event.target.value;
            couName = this.getCityName(this.couList,id);
            this.stuBean.address=proName+cityName+couName;
        },
        changecity:function(event){
            this.coutList=[];
            var id = event.target.value;
            cityName = this.getCityName(this.cityList,id);
            this.stuBean.address=proName+cityName;
            var url="getCityListById.do?id="+id;
            var _this=this;
            axios.get(url).then(function (response) {
                _this.couList=response.data;
            }).catch(function (err) {
                console.log(err)
            });
        },
        changepro:function(event){
            this.cityList=[];
            this.couList=[];
            var id = event.target.value;
            proName = this.getCityName(this.proList,id);
            this.stuBean.address=proName;
            var url="getCityListById.do?id="+id;
            var _this=this;
            axios.get(url).then(function (response) {
                _this.cityList=response.data;
            }).catch(function (err) {
                console.log(err)
            });
        },
        getProList:function(){
            var url="getCityListById.do?id=1";
            var _this=this;
            axios.get(url).then(function (response) {
                _this.proList=response.data;
            }).catch(function (err) {
                console.log(err)
            });
        },
        addStu:function(){
            this.getGradeList();
            document.getElementById("a3").style.display="block";
        },
        getGradeList:function(){
            var url="getGradeList.do";
            var _this = this;
            axios.get(url).then(function (response) {
                _this.glist = response.data;
            }).catch(function (err) {
                console.log(err)
            });
        },
        closeWin:function(){
            if(window.confirm("您确定要没保存之前关掉修改页面吗?")){
                document.getElementById("a3").style.display="none";
            }

        },
        closeWinA4:function(){
            document.getElementById("a4").style.display="none";
        },
        saveStu:function(){
            var url="saveStu.do";
            var _this = this;
            axios.post(url,_this.stuBean).then(function (response) {
                if(response.data.success){
                    _this.findAll();
                    document.getElementById("a3").style.display="none";
                }else{
                    alert(response.data.message);
                }
            }).catch(function (err) {
                console.log(err)
            });


        },
        findOne:function(sid){
            var url="findOne.do?sid="+sid;
            var _this = this;
            axios.get(url).then(function (response) {
                _this.stuBean = response.data;
                _this.getGradeList();
                document.getElementById("a3").style.display="block";
            }).catch(function (err) {
                console.log(err)
            });
        },
        deleteStu:function(sid){
            var url="deleteStuById.do?sid="+sid;
            var _this = this;
            axios.get(url).then(function (response) {
                //console.log(response);
                if(response.data.success){
                    _this.findAll();
                }else{
                    alert(response.data.message);
                }
            }).catch(function (err) {
                console.log(err)
            });
        },
        /**
         * 日期格式化的
         * @param time
         * @returns {string}
         */
        dateFormat:function(time) {
            if(time!=null&&time!=''){
                //这个time是后台接收的参数，是个毫秒值，先把他变成js的时间
                var date=new Date(time);

                //date.getYear不可以用，他获取的是从1900年到现在的年份差，不能用
                var year=date.getFullYear();

                /* 在日期格式中，月份是从0开始的，因此要加  yyyy——MM-dd这的，1-9月份，前面要加一个0
                    要是月份加1
                 * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
                 * */
                var month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
                var day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
                var hours=date.getHours()<10 ? "0"+date.getHours() : date.getHours();
                var minutes=date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
                var seconds=date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
                // 拼接
                return year+"-"+month+"-"+day;
            }else{
                return "";
            }

        },
        findAll:function () {
            this.findStuList();
            /**
             * ajax去查询，所以要先把ajax导入进来

            var url="findAll.do";

            var _this = this;
            axios.get(url).then(function (response) {
                //console.log(response);
                _this.stulist = response.data;
            }).catch(function (err) {
                console.log(err)
            });
             */
        }
    },
    created:function () {
        this.findStuList();
    }
});