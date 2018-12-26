import  config from '../config'

const url = config.url;

export const receiveGetProducts = (data) =>( {
    type: 'getProducts',
    data: data,
} );

export const receiveGetProduct = (data) =>( {
    type: 'getProduct',
    data: data,
} );


export const clearProduct = () =>( {
    type: 'clearProduct',
} );

export const receiveDeleteProduct = (data) =>( {
    type: 'deleteProduct',
    data: data,
} );

export const receiveUpdateProduct = (data) =>( {
    type: 'updateProduct',
    data: data,
} );

export const receiveCreateProduct = (data) =>( {
    type: 'createProduct',
    data: data,
} );

export const receiveSearchProduct = (data) =>( {
    type: 'searchProduct',
    data: data,
} );

export const getProducts = failure => dispatch => {
    fetch(`${url}/api/products`)
        .then(resp => {
            console.log('get prodcts');
            return resp.json()
        })
        .then(resp => {

            if (resp.error) {
                failure(resp.message);
            } else {
                console.log(resp);

                dispatch(receiveGetProducts(resp));
            }
        })
        .catch(err => {
            console.log(err.message);
            if (failure) failure(err.message);
        });
};



export const searchProduct = (searchStr)=> dispatch => {
    console.log('search products');
    fetch(`${url}/api/products?name=${searchStr}`)
        .then(resp => {
            console.log('search products');
            return resp.json()
        })
        .then(resp => {

            if (resp.error) {
            } else {
                console.log(resp);

                dispatch(receiveSearchProduct(resp));
            }
        })
        .catch(err => {
            console.log(err.message);
        });
};

export const getProduct = (product, failure) => dispatch => {
    fetch(`${url}/api/products/${product.id}`)
        .then(resp => resp.json())
        .then(resp => {
            if (resp.error) {
                failure(resp.message);
            } else {
                dispatch(receiveGetProduct(resp));
            }
        })
        .catch(err => {
            console.log(err);
            if (failure) failure(err.message);
        });
};
export const deleteProduct = (product, success, failure) => dispatch => {
    fetch(`${url}/api/products/${product.id}`, {
        method: "DELETE"
    })
        .then(resp => {
            if (resp.error) {
                failure(resp.message);
            } else {
                dispatch(receiveDeleteProduct(product));
                if (success) success();
            }
        })
        .catch(err => {
            console.log(err);
            if (failure) failure(err.message);
        });
};

export const createProduct = (product, success, failure) => dispatch => {
    console.log("create", product);
    fetch(`${url}/api/products`, {
        method: "POST",
        headers: {"content-type": "application/json", 'Accept': 'application/json'},
        body: JSON.stringify(product)
    })
        .then(resp => {
            return resp.json()
        })
        .then(resp => {
            if (resp.error) {
                failure(resp.message);
            } else {
                dispatch(receiveCreateProduct(resp));
                if (success) success();
            }
        })
        .catch(err => {
            console.log(err.message);
            if (failure) failure(err.message);
        });
};

export const updateProduct = (product, success, failure) => dispatch => {
    console.log("update", product);
    fetch(`${url}/api/products/${product.id}`, {
        method: "PUT",
        headers: {"content-type": "application/json"},
        body: JSON.stringify(product)
    })
        .then(resp => resp.json())
        .then(resp => {
            if (resp.error) {
                failure(resp.message);
            } else {
                dispatch(receiveUpdateProduct(resp));
                if (success) success();
            }
        })
        .catch(err => {
            console.log(err);
            if (failure) failure(err.message);
        });
};
