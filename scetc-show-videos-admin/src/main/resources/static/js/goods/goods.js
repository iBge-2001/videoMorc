function enableOrDisableGoods (params) {
    return $axios({
        url: '/Goods/check',
        method: 'put',
        data: { ...params }
    })
}
function InsertGoods(params) {
    return $axios({
        url: '/Goods/insert',
        method: 'post',
        data: { ...params }
    })
}