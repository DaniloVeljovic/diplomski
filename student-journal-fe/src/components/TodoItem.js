import React, { Component } from 'react';
import Typography from '@material-ui/core/Typography'


export class TodoItem extends Component {



    getStyle = () => {
        return {
            background: '#f4f4f4',
            padding: '10px',
            border: '1px #ccc dotted',
            margin: '4px 4px 4px 4px'
        }
    }





    render() {

        const { course } = this.props.todo;

        return (
            <div onClick={this.props.eventHandler.bind(this, course)} style={this.getStyle()} >
                <Typography variation="h6"> {course}</Typography>
            </div>
        )
    }
}


export default TodoItem

/* render() {

        const { id, title } = this.props.todo;

        return (
            <div style={this.getStyle()}>
                <Link to="/notebook">Notebook name</Link>
                <input type="checkbox" onChange={this.props.markComplete.bind(this, id)} />
                {' '}
                {title}
                <button onClick={this.props.delTodo.bind(this, id)} style={btnStyle}>x</button>
            </div>
        )
    } */

/*getStyle = () => {
    return {
        textDecoration: this.props.todo.completed ? 'line-through' : 'none',
        background: '#f4f4f4',
        padding: '10px',
        borderBottom: '1px #ccc dotted',
        margin: '4px 4px 4px 4px'
    }
} */