import React, { Component } from 'react'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'
import axios from 'axios'


export default class Notebooks extends Component {

    state = {
        value: {},
        toggle: true
    }

    componentDidMount() {

    }

    updateNotebook = (ev) => {
        console.log(this.state.value)

        axios.put('http://localhost:8100/notebook-microservice/notebooks/students/' + this.props.userID,
            {
                text: this.state.value,
                course: this.props.activeNotebook.course
            })
            .then((res) => {
                console.log(res)
                this.setState({ value: res.text })
                this.props.rerender.bind(this, this.state.toggle)
                const c = !this.state.toggle
                this.setState({ toggle: c })
            })

    }

    changeState = (a) => {
        console.log(a.target.value)
        this.setState({ value: a.target.value })
    }

    render() {
        return (
            <div>

                <TextField
                    id="filled-multiline-static"
                    multiline
                    rows={50}
                    defaultValue={this.props.activeNotebook.text}
                    variant="filled"
                    fullWidth={true}
                    onChange={this.changeState}
                />
                <Button onClick={this.updateNotebook}> Sacuvaj promene </Button>

            </div>
        )
    }
}
