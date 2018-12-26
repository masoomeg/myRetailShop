import { createStore, applyMiddleware, compose ,combineReducers } from "redux";
import thunk from "redux-thunk";
import productReducer from "./reducers/productReducer";

const initialState = {};
const middleware = [thunk];
const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

const store = createStore(
    combineReducers({
      reducer: productReducer
    })
  ,
  initialState,

  composeEnhancers(applyMiddleware(...middleware))
);

export default store;


