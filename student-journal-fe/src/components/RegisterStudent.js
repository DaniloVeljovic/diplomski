import React, { Component } from 'react'
import Grid from '@material-ui/core/Grid'
import Button from '@material-ui/core/Button'
import TextField from '@material-ui/core/TextField'
import Card from '@material-ui/core/Card'
import Typography from '@material-ui/core/Typography'
import Avatar from '@material-ui/core/Avatar'
import axios from 'axios'


export default class RegisterStudent extends Component {

    state = {
        name: '',
        email: '',
        role: 'student',
        surname: '',
        avgGrade: '',
        yearOfStudy: '',
        numIndex: '',
        password: ''
    }

    changeName = (ev) => {
        this.setState({ name: ev.target.value })
    }

    changeEmail = (ev) => {
        this.setState({ email: ev.target.value })
    }

    changePassword = (ev) => {
        this.setState({ password: ev.target.value })
    }

    changeSurname = (ev) => {
        this.setState({ surname: ev.target.value })
    }

    changeAvgGrade = (ev) => {
        this.setState({ avgGrade: ev.target.value })
    }

    changeNumIndex = (ev) => {
        this.setState({ numIndex: ev.target.value })
    }

    changeYearOfStudy = (ev) => {
        this.setState({ yearOfStudy: ev.target.value })
    }

    change = () => {
        const usertoSend = {
            'name': this.state.name,
            'surname': this.state.surname,
            'password': this.state.password,
            'email': this.state.email,
            'avgGrade': this.state.avgGrade,
            'numIndex': this.state.numIndex,
            'yearOfStudy': this.state.yearOfStudy,
            'role': this.state.role
        }
        console.log(usertoSend)
        console.log(this.props)
        this.props.register(usertoSend)
    }

    render() {



        return (
            <React.Fragment>
                <Card>
                    <Grid container direction="column" justify='center' spacing={2} alignItems="stretch">

                        <Grid item> <Grid container direction="row" justify='center'> <Grid item >  <Typography variant="h2"> Prijavi se </Typography> </Grid> </Grid> </Grid>
                        <Grid item>
                            <Grid container direction="row" justify='space-evenly' spacing={1}>

                                <Grid item>
                                    <Grid container spacing={2} direction="column" justify="space-evenly" alignItems="center">
                                        <Grid item> <TextField multiline={false} onChange={this.changeName} variant="filled" label='ime' /></Grid>
                                        <Grid item> <TextField multiline={false} onChange={this.changeSurname} variant="filled" label='prezime' /></Grid>
                                        <Grid item> <TextField multiline={false} onChange={this.changeEmail} variant="filled" label='email' /></Grid>
                                        <Grid item> <TextField multiline={false} onChange={this.changePassword} variant="filled" type='password' label='lozinka' /></Grid>
                                        <Grid item> <TextField multiline={false} onChange={this.changeYearOfStudy} variant="filled" label='godina studija' /></Grid>
                                        <Grid item> <TextField multiline={false} onChange={this.changeAvgGrade} variant="filled" label='prosecna ocena' /></Grid>
                                        <Grid item> <TextField multiline={false} onChange={this.changeNumIndex} variant="filled" label='broj indeksa' /></Grid>
                                    </Grid>
                                </Grid>

                            </Grid>
                        </Grid>
                        <Grid item>
                            <Grid container direction="row" justify='center'>
                                <Grid item>
                                    <Button onClick={this.change} variant="contained" size="large" color="primary"> Registruj se </Button>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                </Card>
            </React.Fragment>
        )
    }
}
