import React, { Component } from 'react'
import Grid from '@material-ui/core/Grid'
import Button from '@material-ui/core/Button'
import TextField from '@material-ui/core/TextField'
import Card from '@material-ui/core/Card'
import Typography from '@material-ui/core/Typography'
import Avatar from '@material-ui/core/Avatar'
import axios from 'axios'


export default class Profile extends Component {

    state = {
        user: {},
        name: '',
        email: '',
        role: '',
        surname: '',
        avgGrade: '',
        yearOfStudy: '',
        numIndex: '',
        userID: this.props.userID
    }

    changeName = (ev) => {
        this.setState({ name: ev.target.value })
    }

    changeEmail = (ev) => {
        this.setState({ email: ev.target.value })
    }

    changeRole = (ev) => {
        this.setState({ role: ev.target.value })
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
            'name': (this.state.name === '') ? this.state.user.name : this.state.name,
            'surname': (this.state.surname === '') ? this.state.user.surname : this.state.surname,
            'email': (this.state.email === '') ? this.state.user.email : this.state.email,
            'avgGrade': (this.state.avgGrade === '') ? this.state.user.avgGrade : this.state.avgGrade,
            'numIndex': (this.state.numIndex === '') ? this.state.user.numIndex : this.state.numIndex,
            'yearOfStudy': (this.state.yearOfStudy === '') ? this.state.user.yearOfStudy : this.state.yearOfStudy
        }
        console.log(usertoSend)
        this.updateUserInfo(usertoSend)
    }

    updateUserInfo = async (user) => {
        console.log(user)
        let res = await axios.put('http://localhost:8100/users-microservice/users/students/' + this.state.userID, user)
        console.log(res)
        this.setState({
            user: res.data
        })
    }

    getStudentInformation = async () => {
        let res = await axios.get('http://localhost:8100/users-microservice/users/students/' + this.props.userID)

        console.log("primljeno je ");
        console.log(res.data)
        this.setState({
            user: res.data,

        }, () => {
            console.log("Sadasnje stanje user objekta je: ")
            console.log(this.state.user)
        })

    }

    componentWillMount() {
        this.getStudentInformation();

    }

    render() {



        return (
            <React.Fragment>
                <Card>
                    <Grid container direction="column" justify='center' spacing={2} alignItems="stretch">
                        <Grid item> <Grid container direction="row" justify='center'> <Grid item><Avatar variant="square" >A</Avatar></Grid> </Grid> </Grid>
                        <Grid item>
                            <Grid container direction="row" justify='space-evenly' spacing={1}>
                                <Grid item>
                                    <Grid spacing={3} container direction="column" justify='space-evenly' alignItems="stretch">
                                        <Grid item md={2}>  <Typography variant="h6"> Ime </Typography> </Grid>
                                        <Grid item md={2}>  <Typography variant="h6"> Prezime </Typography> </Grid>
                                        <Grid item md={2}>  <Typography variant="h6"> Email </Typography> </Grid>
                                        <Grid item md={2}>  <Typography variant="h6"> Godina studija </Typography> </Grid>
                                        <Grid item md={2}>  <Typography variant="h6"> Prosečna ocena </Typography> </Grid>
                                        <Grid item md={2}>  <Typography variant="h6"> Broj indeksa </Typography> </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item>
                                    <Grid container spacing={2} direction="column" justify="space-evenly" alignItems="center">
                                        <Grid item> <TextField multiline={false} onChange={this.changeName} variant="filled" placeholder={this.state.user.name} /></Grid>
                                        <Grid item> <TextField multiline={false} onChange={this.changeSurname} variant="filled" placeholder={this.state.user.surname} /></Grid>
                                        <Grid item> <TextField multiline={false} onChange={this.changeEmail} variant="filled" placeholder={this.state.user.email} /></Grid>
                                        <Grid item> <TextField multiline={false} onChange={this.changeYearOfStudy} variant="filled" placeholder={this.state.user.yearOfStudy} /></Grid>
                                        <Grid item> <TextField multiline={false} onChange={this.changeAvgGrade} variant="filled" placeholder={this.state.user.avgGrade} /></Grid>
                                        <Grid item> <TextField multiline={false} onChange={this.changeNumIndex} variant="filled" placeholder={this.state.user.numIndex} /></Grid>
                                    </Grid>
                                </Grid>

                            </Grid>
                        </Grid>
                        <Grid item>
                            <Grid container direction="row" justify='center'>
                                <Grid item>
                                    <Button onClick={this.change} variant="contained" size="large" color="primary"> Promeni podatke </Button>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                </Card>
            </React.Fragment>
        )
    }
}
