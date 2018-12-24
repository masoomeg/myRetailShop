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

export const getProducts = failure => dispatch => {
    fetch(`${url}/products`)
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
export const getProduct = (product, failure) => dispatch => {
    fetch(`${url}/products/${product.id}`)
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
    fetch(`${url}/products/${product.id}`, {
        method: "DELETE"
    })
        .then(resp => {
            if (resp.error) {
                failure(resp.message);
            } else {
                dispatch(receiveDeleteProduct(product));
            }
        })
        .catch(err => {
            console.log(err);
            if (failure) failure(err.message);
        });
};

export const createProduct = (product, success, failure) => dispatch => {
    console.log("create", product);
    fetch(`${url}/products`, {
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
    fetch(`${url}/products/${product.id}`, {
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
