const getClassStudentApi =(params)=>  {
    return $axios({
        url: `/video/queryAll`,
        method: 'get',
        params
    })
}