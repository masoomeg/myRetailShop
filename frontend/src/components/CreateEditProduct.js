import React, { Component } from "react";
import { Button, Modal, ModalHeader,ModalBody ,Tooltip,OverlayTrigger} from "react-bootstrap";
import { connect } from "react-redux";
import { createProduct, updateProduct , clearProduct} from "../actions/productActions";
import moment from "moment";

class CreateEditProduct extends Component {
    constructor(props) {
        super(props);
        this.onChange = this.onChange.bind(this);
        this.onSave = this.onSave.bind(this);
        this.onReset = this.onReset.bind(this);
        this.onResetParam = this.onResetParam.bind(this);
        this.onFailure = this.onFailure.bind(this);
        this.onSuccess = this.onSuccess.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);

        if (this.props.product.id) {
            this.state = {
                showModal: true,
            }
        }
        this.state = {
            showModal: false,
            modalMsg: '',
            modalMsgHeader: '',
            name: "",
            description: "",
            category: "",
            amount: 0,
            valid:true,
            errors:{}

        };
    }


    handleValidation = ()=> {
        let errors = {}
        let valid = true;
        const re = /^[0-9\b]+$/;
        if (!this.state.name) {
            errors["name"] = " can not be empty!";
            this.setState({errors: errors, valid: false});
            valid = false;
        }
        if (!re.test(this.state.amount)) {
            errors["Amount"] = " must be numberic!";
            this.setState({errors: errors, valid: false});
            valid = false;
        }
        return valid;
    }
    async handleClose() {

        await this.props.clearProduct();
        this.setState({
            showModal: false,
            modalMsg: '',
            modalMsgHeader: '',
            valid :true,
            errors:{}
        });

    }

    handleShow() {
        this.setState({showModal: true});
    }

    onChange(e) {
        this.setState({[e.target.name]: e.target.value});
    }

    componentWillMount() {
        if (this.props.product.id) {
            this.setState({showModal: true});
        }
    }

    componentDidUpdate(prevProps) {

        if (prevProps.product.id!= this.props.product.id ) {

            if (this.props.product.id) {
                this.setState({showModal: true});
            }else {
                this.onResetParam();
            }
        }
        }

    componentWillReceiveProps(nextProps) {
        if (nextProps.product && nextProps.product.id) {
            this.setState(nextProps.product);
        }
        if (this.props.product.id) {
            this.setState({showModal: true});
        }
    }

    onReset(e) {
        if (e) e.preventDefault();
        this.setState({
            modalMsg: '',
            modalMsgHeader: '',
            name: "",
            description: "",
            category: "",
            amount: 0,
            id: "",
            valid :true,
             errors:{}
        });
    }

    onResetParam(e) {
           if (e) e.preventDefault();
           this.setState({
               name: "",
               description: "",
               category: "",
               amount: 0,
               id: "",
               valid :true,
                errors:{}
           });
       }

    onSave(e) {
        e.preventDefault();
        this.setState({errors:{},valid:true});
       if ( !this.handleValidation()){
           return;
       }
        const product = {
            name: this.state.name,
            description: this.state.description,
            category: this.state.category ? this.state.category : "Pet",
            amount: this.state.amount,
        };
        if (this.state.id) {
            product["id"] = this.state.id;
            this.props.updateProduct(
                product,
                this.onSuccess,
                this.onFailure
            );
        } else {
            this.props.createProduct(
                product,
                this.onSuccess,
                this.onFailure
            );
        }
    }

    onSuccess() {
        this.setState({
            showModal: true,
            modalMsg: 'your changes was affected successfully',
            modalMsgHeader: 'Congratulations!'
        });
        this.onResetParam();
        this.props.clearProduct();
    }

    onFailure(msg) {
        this.setState({showModal: true, modalMsg: msg, modalMsgHeader: 'Failure!!'});
    }

    render() {
        const categories = [{value: "Electronic", label: "Electronic"},
            {value: "Health", label: "Health And Beauty"},
            {value: "Pet", label: "Pet"},
            {value: "Home", label: "Home And Furniture"},
            {value: "logo", label: "other"}];

        const tooltip = (
            <Tooltip id="tooltip">
                <strong>Click to add new Product!</strong>
            </Tooltip>
        );
        return (

            <div className="createProduct">
                <OverlayTrigger placement="left" overlay={tooltip}>
                    <i onClick={this.handleShow} className="fa fa-plus-circle"
                       style={{ fontSize: '37px' , color:'#79e079'}}/>
                </OverlayTrigger>
                <div className="panel panel-default  ">


                    <Modal show={this.state.showModal} onHide={this.handleClose}>
                        <Modal.Header closeButton>
                            <Modal.Title>{this.state.modalMsgHeader}</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <h4>{this.state.modalMsg}</h4>

                            <form className="panel-body">
                                <div className="form-row">
                                    <div className="form-group col-md-6">
                                        <label htmlFor="name">Product Name</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            name="name"
                                            id="name"
                                            value={this.state.name}
                                            placeholder="Product Name"
                                            onChange={this.onChange}
                                        />
                                    </div>
                                    <div className="form-group col-md-6">
                                        <label htmlFor="amount">Amount</label>
                                        <input
                                            type="text"
                                            pattern="[0-9]*"
                                            inputMode="numeric"
                                            className="form-control"
                                            id="amount"
                                            value={this.state.amount}
                                            name="amount"
                                            placeholder="Amount"
                                            onChange={this.onChange}
                                        />
                                    </div>
                                </div>

                                <div className="form-row">
                                    <div className="form-group col-md-6">
                                        <label htmlFor="description">Description</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="description"
                                            name="description"
                                            value={this.state.description}
                                            placeholder="Describe this product ..."
                                            onChange={this.onChange}
                                        />
                                    </div>
                                    <div className="form-group col-md-6">
                                        <label htmlFor="type">Category</label>
                                        <select
                                            defaultValue={this.state.category}
                                            onChange={this.onChange}
                                            id="category"
                                            name="category"
                                            className="form-control"
                                        >
                                            <option>Choose...</option>
                                            {categories.map(
                                                category => {
                                                    return (
                                                        <option
                                                            key={category.value}
                                                            value={category.value}
                                                        >
                                                            {category.label}
                                                        </option>
                                                    );
                                                }
                                            )}
                                        </select>
                                    </div>
                                    <div className="form-group col-md-12">

                                        <Button style={{margin:"5px"}}
                                                onClick={this.onSave}
                                                className="btn btn-success col-md-2"
                                        >
                                            Save
                                        </Button>


                                        <Button style={{margin:"5px"}}
                                                onClick={this.onReset}
                                                className="btn btn-danger col-md-2"
                                        >
                                            Reset
                                        </Button>
                                        {!this.state.valid &&  <div>{ Object.keys(this.state.errors).map((val, k) => <h4 style={{color:"red"}} k={k}>{val}{ this.state.errors[val]}</h4>)}</div>}
                                    </div>
                                </div>
                            </form>


                        </Modal.Body>
                    </Modal>

                </div>
            </div>
        );
    }
}

const mapStateToProps = state => ({
    product: state.reducer.product
});
export default connect(
    mapStateToProps,
    {createProduct, updateProduct, clearProduct}
)(CreateEditProduct);
