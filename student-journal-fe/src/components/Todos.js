import React, { Component } from 'react';
import TodoItem from './TodoItem';
import Notebook from './Notebooks'
import axios from 'axios'



class Todos extends Component {


    getNotebooksForUser = async () => {
        let res = await axios.get('http://localhost:8100/notebook-microservice/notebooks/students/' + this.props.userID)

        console.log("primljeno je ");
        console.log(res.data)
        this.setState({
            notebooks: res.data
        }, () => {
            console.log("Sadasnje stanje user objekta je: ")
            console.log(this.state.user)
        })

    }

    componentWillMount() {
        console.log("pre svega")
        this.getNotebooksForUser();
    }

    state = {
        notebooks: [],
        activeNotebook: {},
        showNotebook: false,
        value: false
    }

    eventHandler = (ev) => {
        let toggle = this.state.showNotebook;
        const found = this.state.notebooks.find(notebook => notebook.course === ev)
        this.setState({ activeNotebook: found, showNotebook: !toggle })
        this.mount();
    }

    async mount() {
        const res = await axios.get('http://localhost:8100/notebook-microservice/notebooks/students/' + this.props.userID)
        console.log("primljeno je ");
        console.log(res.data)
        this.setState({
            notebooks: res.data
        }, () => {
            console.log("AAAAAAAAAAAAAAAAAAA ")
            console.log(this.state.user)
        })
    }

    changeState = (value) => {
        const val = this.state.value
        this.setState({ toggle: !val })
    }

    render() {

        let show = null;

        if (this.state.showNotebook) {
            show = (<div> <Notebook userID={this.props.userID}
                activeNotebook={this.state.activeNotebook} rerender={this.changeState} />  </div>)
        }

        return <React.Fragment>
            {show}
            {this.state.notebooks.map((todo) => (
                <TodoItem key={todo.course} todo={todo} eventHandler={this.eventHandler} />
            ))}


        </React.Fragment>
    }
}


export default Todos;