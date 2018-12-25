
const initialState = {
  products: [],
  product: {}
};
export default function(state = initialState, action) {
  switch (action.type) {
    case 'getProducts': {
      return {
        ...state, products: action.data};
    }
      case 'searchProduct': {
            return {
              ...state, products: action.data};
          }
    case 'createProduct': {
      const new_products = state.products.filter(
              p => p.id !== action.data.id
            );
            new_products.unshift(action.data);
            return {
             ...state,
              product: action.data,
              products: new_products
            };
    }
    case 'getProduct': {
      return { ...state, product: action.data};
    }

    case 'clearProduct': {
          return { ...state, product: {}};
        }
    case 'deleteProduct': {
      return {
        ...state, product: {}, products: state.products.filter(p => p.id !== action.data.id)
      };
    }
    case 'updateProduct': {
      const new_products = state.products.filter(
        p => p.id !== action.data.id
      );
      new_products.unshift(action.data);
      return {
        ...state,
        product: action.data,
        products: new_products
      };
    }
    default:
      return state;
  }
}
