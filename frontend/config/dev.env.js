'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  AUTH0_CLIENT_ID: '"p72KxWJUeq8LrhTZqrcp2WOcpW13GXrl"',
  AUTH0_DOMAIN: '"arcofcollaboration.eu.auth0.com"'
})
