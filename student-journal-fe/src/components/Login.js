import React, { Component } from 'react'
import Grid from '@material-ui/core/Grid'
import Button from '@material-ui/core/Button'
import TextField from '@material-ui/core/TextField'
import Card from '@material-ui/core/Card'
import Typography from '@material-ui/core/Typography'
import Avatar from '@material-ui/core/Avatar'
import Link from '@material-ui/core/Link'

export default class Profile extends Component {

    state = {

        email: {},
        password: {},
    }



    changeEmail = (ev) => {
        this.setState({ email: ev.target.value })
    }



    changePassword = (ev) => {
        this.setState({ password: ev.target.value })
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
                                    <Grid spacing={7} container direction="column" justify='space-evenly' alignItems="stretch">
                                        <Grid item >  <Typography variant="h6"> Email </Typography> </Grid>
                                        <Grid item >  <Typography variant="h6"> Lozinka </Typography> </Grid>

                                    </Grid>
                                </Grid>
                                <Grid item>
                                    <Grid container spacing={2} direction="column" justify="space-evenly" alignItems="center">
                                        <Grid item> <TextField multiline={false} onChange={this.changeEmail} variant="filled" placeholder={this.state.name} /></Grid>
                                        <Grid item> <TextField multiline={false} onChange={this.changePassword} type='password' variant="filled" placeholder={this.state.surname} /></Grid>

                                    </Grid>
                                </Grid>

                            </Grid>
                        </Grid>
                        <Grid item>
                            <Grid container direction="row" justify='center'>
                                <Grid item>
                                    <Button onClick={this.props.change.bind(this, this.state)} variant="contained" size="large" color="primary"> Prijavi se </Button>
                                </Grid>
                            </Grid>
                        </Grid>

                        <Grid item>
                            <Grid container justify='flex-end'>
                                <Grid item>
                                    <Grid container direction='column'>
                                        <Grid item> <Link href="/registerStudent"> Registruj se kao student </Link> </Grid>
                                        <Grid item> <Link href="/registerTeacher"> Registruj se kao nastavnik </Link> </Grid>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                </Card>
            </React.Fragment>
        )
    }
}
